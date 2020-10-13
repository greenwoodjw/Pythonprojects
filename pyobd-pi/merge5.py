# Copyright 2018 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# [START drive_quickstart]
from __future__ import print_function
import pickle
import os.path
import subprocess
import gps
import time
import fnmatch
import busio
import digitalio
import board
import sys, getopt
import adafruit_mcp3xxx.mcp3008 as MCP
#begin obd import
import obd_io3
import serial
import platform
import obd_sensors
from datetime import datetime
import time
import getpass

from adafruit_mcp3xxx.analog_in import AnalogIn
from googleapiclient.discovery import build
from google_auth_oauthlib.flow import InstalledAppFlow
from google.auth.transport.requests import Request
from apiclient.http import MediaFileUpload
from apiclient.discovery import build
from filehash import FileHash
from obd_utils import scanSerial


###begin accelerometer create
#create spi bus
spi = busio.SPI(clock=board.SCK, MISO=board.MISO, MOSI=board.MOSI)

#  second SPI bus for future gyroscope
#  add  dtoverlay=spi1-3cs to bottom of /boot/config.txt
#spi = busio.SPI(board.SCK_1, MOSI=board.MOSI_1, MISO=board.MISO_1)
#CK_1 on GPIO #21
#MOSI_1 on GPIO #20
#MISO_1 on GPIO #19
#SPI #1 CS0 on GPIO 18
#SPI #1 CS1 on GPIO 17
#SPI #1 CS2 on GPIO 16

###OBD Log Items
logitems = ["rpm", "speed", "throttle_pos", "load", "fuel_status"]
###obd logitems

# create the cs (chip select)
cs = digitalio.DigitalInOut(board.D22)

# create the mcp object
mcp = MCP.MCP3008(spi, cs)

# create an analog input channel on pin 0
chan0 = AnalogIn(mcp, MCP.P0)
chan1 = AnalogIn(mcp, MCP.P1)
chan2 = AnalogIn(mcp, MCP.P2)

last_read = 0       # this keeps track of the last potentiometer value
tolerance = 64     # to keep from being jittery we'll only change
                    # volume when the pot has moved a significant amount
                    # on a 16-bit ADC


###end accelerometer# create the spi bus





###begin log shipping to GDrive

SCOPES = ['https://www.googleapis.com/auth/drive']




def connectandship():

    creds = None
    # The file token.pickle stores the user's access and refresh tokens, and is
    # created automatically when the authorization flow completes for the first
    # time.
    if os.path.exists('token.pickle'):  #requires local execution
        with open('token.pickle', 'rb') as token:
            creds = pickle.load(token, encoding='iso-8859-1')
    # If there are no (valid) credentials available, let the user log in.
    if not creds or not creds.valid:
        if creds and creds.expired and creds.refresh_token:
            creds.refresh(Request())
        else:
            flow = InstalledAppFlow.from_client_secrets_file(
                'credentials.json', SCOPES)
            creds = flow.run_local_server()
        # Save the credentials for the next run
        with open('token.pickle', 'wb') as token:
            pickle.dump(creds, token)

    try:
        service = build('drive', 'v3', credentials=creds)
        shipFile(service)
    except:
        print("Cannot Connect to GDrive at this time")
        #print(fnmatch.filter(os.listdir('.'),'outputDRIVE*.txt*'))
    #shipFile(service)

def shipFile(service):
#begin checksum calculation for log files
    hashing_files = fnmatch.filter(os.listdir('.'),'outputDRIVE*.txt')
    for hashing_file in hashing_files:
        md5hasher = FileHash('md5')
        sha1hasher = FileHash('sha1')
        sha512hasher = FileHash('sha512')
        print ("md5 = "+ md5hasher.hash_file(hashing_file)+"\n", file=open(hashing_file+'.hash',"a"))
        print ("\nsha1 = "+ sha1hasher.hash_file(hashing_file)+"\n", file=open(hashing_file+".hash","a"))
        print ("\nsha512 = "+ sha512hasher.hash_file(hashing_file)+"\n", file=open(hashing_file+".hash","a"))
###end checksum


###shipping of logs and checksums
    shipping_files = fnmatch.filter(os.listdir('.'),'outputDRIVE*.txt*')
    for shipping_file in shipping_files:
	
        file_metadata={'name':shipping_file}
        log = MediaFileUpload(shipping_file)
        try:
            file=service.files().create(body=file_metadata, media_body=log,fields='id').execute()
            print( 'File ID: %s' % file.get('id') )
            os.remove(shipping_file)
        except IOError:
            print('upload error')
###end shipping
###end gdrive shipping
# end shipFile()

###defines range mapping for potentiometer and MCP3008 ADC chip
####depracated, range remapping for potentiometer
#def remap_range(value, left_min, left_max, right_min, right_max):
#    # this remaps a value from original (left) range to new (right) range
#    # Figure out how 'wide' each range is
#    left_span = left_max - left_min
#    right_span = right_max - right_min
#
#    # Convert the left range into a 0-1 range (int)
#    valueScaled = int(value - left_min) / int(left_span)
#
#    # Convert the 0-1 range into a value in the right range.
#    return int(right_min + (valueScaled * right_span))
#
###end range mapping for potentiometer


###begin ADC conversion for gravity measurement
## note: subtract 0.5 because midpoint of voltage range equals 0 G
## multiply by 3.0 because ADXL335 has range of +/- 3G
###non calibrated, not accurate

def accel_value(axis):  ###simple, non-calibrated calculation
    # Convert axis value to float within 0...1 range.
    val = (axis) / 65535
    #val = axis.value / 65535
    # Shift values to true center (0.5).
    val -= 0.5
    # Convert to gravity range of ADXL355.
    return val * 3.0
##end


###accel_value overload with calibration values unique to device
### calibration values are 10bit due to Arduino limits
### MCP008 uses 16 bit, so we divide axisValue by 2^6 when remapping
def accel_value(axisValue,axis):
    if axis== 0:   #X axis calibration at +/-1G, 10 bit
       rangeLow=405
       rangeHigh=611
       axisValue=axisValue+640
    elif axis==1:  #Y axis calibration
       rangeLow=407
       rangeHigh=616
       axisValue=axisValue+640
    else:# Z axis calibration
       rangeLow=423
       rangeHigh=614
    # Convert to gravities.
    #print("test="+str(remap(axisValue/64,rangeLow,rangeHigh,-1,1)))
    return remap(axisValue/64,rangeLow,rangeHigh,-1,1)   #divide 16 bit axis value by 2^6, send calibration to remap
##end

#begin direct conversion of voltage to Gs, non calibrated
def accel_volt(axis):
    val = axis/3.3
    val -= 0.5
    return val * 3.0
###end

def remap(raw,lowValue,highValue,newLowValue,newHighValue):
    return newLowValue +(raw-lowValue)*(newHighValue-newLowValue)/(highValue-lowValue)

####begin obd



class OBD_Recorder():
    def __init__(self, path, log_items):
        self.port = None
        self.sensorlist = []
        localtime = time.localtime(time.time())
        filename = path+"car-"+str(localtime[0])+"-"+str(localtime[1])+"-"+str(localtime[2])+"-"+str(localtime[3])+"-"+str(localtime[4])+"-"+str(localtime[5])+".log"
        self.log_file = open(filename, "w", 128)
        self.log_file.write("Time,RPM,MPH,Throttle,Load,Fuel Status\n");

        for item in log_items:
            self.add_log_item(item)

        self.gear_ratios = [34/13, 39/21, 36/23, 27/20, 26/21, 25/22]
        #log_formatter = logging.Formatter('%(asctime)s.%(msecs).03d,%(message)s', "%H:%M:%S")

    def connect(self):
        portnames = scanSerial()
        #portnames = ['COM10']
        print(portnames)
        for port in portnames:
            self.port = obd_io3.OBDPort(port, None, 2, 2)
            if(self.port.State == 0):
                self.port.close()
                self.port = None
            else:
                break

        if(self.port):
            print("Connected to OBD"+self.port.port.name)
            
    def is_connected(self):
        return self.port
        
    def add_log_item(self, item):
        for index, e in enumerate(obd_sensors.SENSORS):
            if(item == e.shortname):
                self.sensorlist.append(index)
                print("Logging item: "+e.name)
                break
            
        
    def record_data(self):
        if(self.port is None):
            return None
        
        print("\n\n---Event started")
        
        while 1:
            localtime = datetime.now()
            current_time = str(localtime.hour)+":"+str(localtime.minute)+":"+str(localtime.second)+"."+str(localtime.microsecond)
            log_string = current_time
            results = {}
            for index in self.sensorlist:
                (name, value, unit) = self.port.sensor(index)  ###does this work?
                log_string = log_string + ","+str(value)
                results[obd_sensors.SENSORS[index].shortname] = value;

            #gear = self.calculate_gear(results["rpm"], results["speed"])
            log_string = log_string #+ "," + str(gear)
            self.log_file.write(log_string+"\n")
            #instead of munging, build awesome return


    #def report_OBDdata(self, fp):
    def report_OBDdata(self):
        
        if(self.port is None):
            print("\n---Event started  -- no OBD connection")
            return None
        
        print("\n---Event started")
        
        localtime = datetime.now()
        current_time = str(localtime.hour)+":"+str(localtime.minute)+":"+str(localtime.second)+"."+str(localtime.microsecond)
        display_string = "SYSTEM TIME="+current_time+"\n"
        log_string = current_time+","

        results = {}
        for index in self.sensorlist:
            #print(index)
            (name, value, unit) = self.port.sensor(index)  ###does this work?
            #print(name)
            #print(value)
            #print(unit)
            display_string = display_string +" "+ str(name)+"="+str(value)+" "+str(unit)+"\n"
            log_string = log_string +str(value)+","
            results[obd_sensors.SENSORS[index].shortname] = value;

        #gear = self.calculate_gear(results["rpm"], results["speed"])
        #log_string = log_string #+ "," + str(gear)
        #self.log_file.write(log_string+"\n")
        return log_string,display_string
        #return (name,value,unit)
        #instead of munging, build awesome return   ##tried, only returns last in list

            
    def calculate_gear(self, rpm, speed):
        if speed == "" or speed == 0:
            return 0
        if rpm == "" or rpm == 0:
            return 0

        rps = rpm/60
        mps = (speed*1.609*1000)/3600
        
        primary_gear = 85/46 #street triple
        final_drive  = 47/16
        
        tyre_circumference = 1.978 #meters

        current_gear_ratio = (rps*tyre_circumference)/(mps*primary_gear*final_drive)
        
        #print current_gear_ratio
        gear = min((abs(current_gear_ratio - i), i) for i in self.gear_ratios)[1] 
        return gear
        






###end obd



####begin capture of GPS position and three axis acceleration

def record():
# Listen on port 2947 (gpsd) of localhost
    session = gps.gps("localhost", "2947")
    session.stream(gps.WATCH_ENABLE | gps.WATCH_NEWSTYLE)
    timestr = time.strftime("%Y%m%d-%H%M%S")
    outputfile = '/home/pi/pyobd-pi/outputDRIVE-'+timestr+'.txt'
    o = OBD_Recorder('/home/pi/pyobd-pi/log/', logitems)
    o.sensorlist
    o.connect()

    if not o.is_connected():
        print("Not connected to OBDII")
    if o.is_connected():
        logOBD,displayOBD=o.report_OBDdata()
        #obdName,obdValue,obdUnit=o.report_OBDdata()
        print("hello world, first record")
        print(logOBD)
        print("end first record from def record()")
    #print(obdName+obdValue+obdUnit)
    #print("hello world, record")
    #o.record_data()



    flushcounter=0   # flushing RAM to file write periodically

    try:
        fp = open(outputfile, 'r')
    except IOError:
        fp = open(outputfile, 'w')
    filepath = outputfile


    with open(outputfile, "a") as myfile:
        myfile.write("System Time,RPM,MPH,Throttle,Load,Fuel Status,");
        myfile.write("Sat time,lat,long,alt,sat speed, X,Y,Z\n")
        while True:
            try:
                report = session.next()
                # Wait for a 'TPV' report and display the current time
                # To see all report data, uncomment the line below
                #print(report)
                ####flushcounter=0   # flushing RAM to file write periodically
                flushcounter +=1
                if o.is_connected():
                    logOBD,displayOBD=o.report_OBDdata()
                    print("flushcounter = "+str(flushcounter)+" OBD Data \n "+displayOBD)   
                    #print(displayOBD)   
                    myfile.write(logOBD)###jwg
                    #flushcounter +=1
                else:
                    print("Event started -- \nno OBDII connection\n") 
                if report['class'] == 'TPV':
                    #flushcounter +=1
                    if hasattr(report, 'time'):
                        print("SAT TIME (GMT)= " + str(report.time))
                        myfile.write(str(report.time)+",")
                    if hasattr(report, 'lat'):
                        print(" LAT = " + str(report.lat))
                        myfile.write(str(report.lat)+",")
                    if hasattr(report, 'lon'):
                        print(" LON = " + str(report.lon))
                        myfile.write(str(report.lon)+",")
                    if hasattr(report, 'alt'):
                        print(" ALT = " + str(report.alt))
                        myfile.write(str(report.alt)+" ft,")
                    if hasattr(report, 'speed'):
                        print(" SPEED = " + str("{:.1f}".format(report.speed)))
                        myfile.write(str("{:.1f}".format(report.speed)+" mph,"))
                        #print(report.speed * gps.MPS_TO_KPH)
                    if flushcounter ==60:  #flushing RAM to file periodically
                         fp.flush()
                         #time.sleep(3)
                         #os.fsync(fp)
                         #time.sleep(3)
                         print("flush to file")
                         flushcounter=0
                else:
                   print("no Time-Position-Velocity(TPV) report\n\n\n\n")
                   ###print('---Event Complete\n')
                   ###myfile.write("\n")
                ####  begin xyz move


                myfile.write(' G (calibrated) ' + str("{:.2f}".format(accel_value(chan0.value,0))) + ',' + str("{:.2f}".format(accel_value(chan1.value,1)))+ ',' + str("{:.2f}".format(accel_value(chan2.value,2))) )
                myfile.write("\n")
                print('\nAccelerometer Data')
                #print(' Raw ADC Value: ', chan0.value, ',',chan1.value,',',chan2.value)
                #print(' ADC Voltage: ' + str(chan0.voltage) + ','+ str(chan1.voltage)+','+str(chan2.voltage))
                print(' G (calibrated) ' + str("{:.2f}".format(accel_value(chan0.value,0))) + ',' + str("{:.2f}".format(accel_value(chan1.value,1)))+ ',' + str("{:.2f}".format(accel_value(chan2.value,2))) )



                print('---Event Complete')
                print("\n")




                ####  end xyz move
            except KeyError:
                pass
            except KeyboardInterrupt:
                #  all this is leaving last buffer uncommitted...why?
                #decider = input("Do you want to quit?")
                #if decider == "y":
                #    fp.flush()
                    #time.sleep(15)   #wait for file write complete so we don't miss data
                    #fp.close()
                #    os.fsync(fp)
                #    time.sleep(15)
                #    connectandship() ###ships file on quit, should move out of main()
                #    quit()
                #else:
                    return
            except StopIteration:
                session = None
                print("GPSD has terminated")

###end data capture

def main(argv):

    try:
        opts,args = getopt.getopt(argv,"hc:Cz")
    except getopt.GetoptError:
        print("whoa cant parse")
        sys.exit(2)
    if len(sys.argv) == 1:
        print("just recording")
        record()
    for opt,arg in opts:
        if opt == '-h':
            print("python3 merge3.py -h -c [0-2] -C ")
            print("\t -h help")
            print("\t -c connect to GDrive")
            print("\t\t 0 do not connect to GDrive")
            print("\t\t 1 connect to GDrive first")
            print("\t\t 2 connect to GDrive last")
            print("\t -C connect to GDrive only")
        elif opt == '-C':
            print("connect to GDrive only")
            connectandship()# run beginning and end of trip assuming proximity to home network
        elif opt == '-c':
            #print(opt +", "+arg)
            if arg == '0':
                print("no GDrive connect selected (default)")
                record()
            elif arg == '1':
                print("connecting GDrive first")
                connectandship()# run beginning and end of trip assuming proximity to home network
                record()
            elif arg == '2':
                print("connecting GDrive last")
                record()
                connectandship()# run beginning and end of trip assuming proximity to home network
            else:
                print("value of of range, will not connect to GDrive ")
                record()
        elif opt=='-z':
            print(opt +", "+arg+"do nothing")
        else: 
            print("shouldnt be here")
            #record()

if __name__ == '__main__':

    main(sys.argv[1:])
    #connectandship()
    #record()
    #connectandship()
# [END drive_quickstart]
