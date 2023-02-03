import boto3

ses = boto3.client('ses')

def send_email(to, subject, body):
    response = ses.send_email(
        Destination={
            'ToAddresses': [
                'recipient1@example.com',
            ],
            'CcAddresses': [
                'cc_recipient1@example.com',
                'cc_recipient2@example.com',
            ],
        },
        Message={
            'Subject': {
                'Data': subject,
                'Charset': 'UTF-8'
            },
            'Body': {
                'Html': {
                    'Data': '<html><body><h1>Hello World!</h1></body></html>'
                    'Charset': 'UTF-8'
                }
            }
        },
        Source='your-email@example.com',
    )
    return response

# usage
send_email('recipient@example.com', 'Subject Line', 'Email Body')



# <!DOCTYPE html>
# <html>
#   <head>
#     <meta charset="UTF-8">
#     <title>Simple Emailing Website</title>
#   </head>
#   <body>
#     <h1>Simple Emailing Website</h1>
#     <form id="email-form">
#       <label for="name">Name:</label>
#       <input type="text" id="name" name="name">
#       <br>
#       <label for="email">Email:</label>
#       <input type="email" id="email" name="email">
#       <br>
#       <label for="message">Message:</label>
#       <textarea id="message" name="message"></textarea>
#       <br>
#       <button type="submit">Send Email</button>
#     </form>
#     <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
#     <script>
#       $(document).ready(function() {
#         $("#email-form").submit(function(event) {
#           event.preventDefault();
#           var name = $("#name").val();
#           var email = $("#email").val();
#           var message = $("#message").val();
#           $.ajax({
#             url: "https://your-python-server.com/send-email",
#             type: "POST",
#             data: JSON.stringify({
#               name: name,
#               email: email,
#               message: message
#             }),
#             contentType: "application/json; charset=utf-8",
#             dataType: "json",
#             success: function(data) {
#               console.log(data);
#               alert("Email sent successfully!");
#             },
#             error: function(jqXHR, textStatus, errorThrown) {
#               console.error(textStatus + ": " + errorThrown);
#               alert("Failed to send email. Please try again later.");
#             }
#           });
#         });
#       });
#     </script>
#   </body>
# </html>




from flask import Flask, render_template, request
import smtplib
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart

app = Flask(__name__)

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/send_email', methods=['POST'])
def send_email():
    from_address = "sender@email.com"
    to_address = request.form['to_address']
    subject = request.form['subject']
    message = request.form['message']
    
    msg = MIMEMultipart()
    msg['From'] = from_address
    msg['To'] = to_address
    msg['Subject'] = subject

    msg.attach(MIMEText(message, 'html'))

    try:
        smtpObj = smtplib.SMTP('smtp.gmail.com', 587)
        smtpObj.ehlo()
        smtpObj.starttls()
        smtpObj.ehlo()
        smtpObj.login(from_address, "password")
        smtpObj.sendmail(from_address, to_address, msg.as_string())
        smtpObj.quit()
        return 'Email sent successfully'
    except Exception as e:
        return 'Error: Unable to send email: ' + str(e)

if __name__ == '__main__':
    app.run(debug=True)