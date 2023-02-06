
from flask import Flask, request
from flask_cors import CORS, cross_origin
import json

app = Flask(__name__)
CORS(app)

@app.route('/', methods = ['GET', 'POST'])
def hello():
    return "Hello World!"

@app.after_request
def after_request(response):
    header = response.headers
    header['Access-Control-Allow-Origin'] = '*'
    header['Access-Control-Allow-Methods'] = 'GET, POST, PUT, DELETE, OPTIONS'
    header['Access-Control-Allow-Headers'] = 'Content-Type,Authorization'
    return response

def handle_request(data):
    if data.get("username") == "test" and data.get("password") == "test":
        return {"success": True}
    else: return {"success": False}

@app.route('/login', methods=['GET', 'POST'])
@cross_origin()
def handle_login():
    if request.method == 'POST':
        response = handle_request(request.get_json())
    elif request.method == 'GET':
        response = handle_request(request.form)
    return json.dumps(response)

app.run()