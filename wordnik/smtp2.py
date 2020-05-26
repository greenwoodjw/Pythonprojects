import smtplib, ssl


from email import encoders
from email.mime.base import MIMEBase
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText


port = 587  # For starttls
smtp_server = "smtp.gmail.com"
sender_email = "jameswgreenwood@gmail.com"
receiver_email = "jameswgreenwood@gmail.com"
#password = input("Type your password and press enter:")
password = "enfmdwbqltiiuyuh"
message = MIMEMultipart()
message = """\
Subject: Hi there

This message is sent from Python."""
####
message.attach(MIMEText(body, "plain"))
filename = "output.txt"  # In same directory as script

# Open PDF file in binary mode
with open(filename, "rb") as attachment:
    # Add file as application/octet-stream
    # Email client can usually download this automatically as attachment
    part = MIMEBase("application", "octet-stream")
    part.set_payload(attachment.read())

# Encode file in ASCII characters to send by email    
encoders.encode_base64(part)

# Add header as key/value pair to attachment part
part.add_header(
    "Content-Disposition",
    f"attachment; filename= {filename}",
)

# Add attachment to message and convert message to string
message.attach(part)
text = message.as_string()

####

context = ssl.create_default_context()
with smtplib.SMTP(smtp_server, port) as server:
    server.ehlo()  # Can be omitted
    server.starttls(context=context)
    server.ehlo()  # Can be omitted
    server.login(sender_email, password)
    #server.sendmail(sender_email, receiver_email, message)
    server.sendmail(sender_email, receiver_email, text)
