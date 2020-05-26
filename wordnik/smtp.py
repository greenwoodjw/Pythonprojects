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


context = ssl.create_default_context()
with smtplib.SMTP(smtp_server, port) as server:
    server.ehlo()  # Can be omitted
    server.starttls(context=context)
    server.ehlo()  # Can be omitted
    server.login(sender_email, password)
    server.sendmail(sender_email, receiver_email, message)
