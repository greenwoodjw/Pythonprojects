import os
import getopt
import sys
import time

def main(argv):
    verbose = 'false'

    articledir = "/Users/jimgreenwood/PycharmProjects/subj/Articles/"
    timestr = time.strftime("%Y%m%d-%H%M%S")
    outputfile = '/Users/jimgreenwood/PycharmProjects/subj/output-'+timestr+'.txt'

    try:
        opts, args = getopt.getopt(argv, "vhi:o:", ["ifile=", "ofile="])  #: has value, no colon no value
    except getopt.GetoptError:
        print('test.py -i <inputdir> -o <outputfile> -v')
        sys.exit(2)
    for opt, arg in opts:
        if opt == '-h':
            print('test.py -i <inputdirectory> -o <outputfile>')
            sys.exit()
        elif opt in ("-i", "--idir"):
            articledir = arg
        elif opt in ("-o", "--ofile"):
            outputfile = arg
        elif opt == '-v':
            verbose = 'true'
    #print('Input file is ', inputfile)
    print('Output file is ', outputfile)

    articles = os.listdir(articledir)
    #articles = [f for f in os.listdir(articledir) if os.path.isfile(f)]
    #files = [f for f in os.listdir('.') if os.path.isfile(f)]

    myFile = open(outputfile, "a")

    print("=========================\nBegin\n")
    print("=========================")
    myFile.write("=========================\nBegin\n")
    myFile.write("=========================\n")

    for article in articles:
        print(article)
        positiveCount=0
        negativeCount=0
        neutralCount=0
        bothCount=0
        doc_words=0
        searchfile = open(articledir+ article, "r")  #open next article in list
        lineCount=0  #reset line count for new article

        print("Article Name:"+ articledir + article)
        print("Analyzing...")
        myFile.write("Article Name:"+ articledir + article +"\n")
        myFile.write("Analyzing...\n")
        subjfile=open("subjectivityIndex.txt","r")

        for line in searchfile:
            lineCount+=1 #increment line location in article
            #doc_words +=len(line.split())
            #doc_words = (line.split())
            #words = line.split()
            #print(line.count(" "))
            doc_words = doc_words + line.count(" ")
            for subjword in subjfile:  #parse subjectivity index line
                list_of_items_in_subjword = subjword.split(",")
                type = (list_of_items_in_subjword[0])
                len = (list_of_items_in_subjword[1])
                word = (list_of_items_in_subjword[2])
                pos = (list_of_items_in_subjword[3])#part of speech
                polarity = (list_of_items_in_subjword[4])
                word = " "+word+" "   ##exact word
                #word = " "+word
                if word in line:
                   print("Line # = "+str(lineCount))
                   print("HIT:"+type+" "+" "+ polarity +"word="+word+"\n"+line)
                   myFile.write("\nLine # = "+str(lineCount)+"\n")
                   myFile.write("HIT:"+type+" "+" "+ polarity +"word="+word+"\n"+line)
                   if "positive" in polarity :positiveCount+=1
                   if "negative" in polarity: negativeCount+=1
                   if "neutral" in polarity: neutralCount += 1
                   if "both" in polarity: bothCount += 1
            subjfile.seek(0)
        searchfile.close()
        print("\nArticle Name:"+ articledir + article)
        print("positive count = "+str(positiveCount))
        print("negative count = "+str(negativeCount))
        print("neutral count = " + str(neutralCount))
        print("both count = " + str(bothCount) )
        print("% bias = "+ str('{0:.3g}'.format(100*(positiveCount+negativeCount)/doc_words))+"%")
        print("total words in documents = "+ str(doc_words)+"\n\n")
        print("=========================\nEND ARTICLE\n")
        print("=========================")
        myFile.write("\nArticle Name:"+ articledir + article+"\n")
        myFile.write("positive count = "+str(positiveCount)+"\n")
        myFile.write("negative count = "+str(negativeCount)+"\n")
        myFile.write("neutral count = " + str(neutralCount)+"\n")
        myFile.write("both count = " + str(bothCount)+"\n\n" )
        myFile.write("% bias = "+ str('{0:.3g}'.format(100*(positiveCount+negativeCount)/doc_words))+"%")
        myFile.write("total words in documents = "+ str(doc_words)+"\n\n")


        myFile.write("=========================\nEND ARTICLE\n")
        myFile.write("=========================\n")

if __name__ == "__main__":
    main(sys.argv[1:])
    # smtp3.sendList()
