from flask import Flask, request, jsonify, make_response
from flask_cors import CORS
import ssl

app = Flask(__name__)
CORS(app)

@app.route("/login", methods=["POST"])
def handle_login():
    data = request.get_json()
    username = data.get("username")
    password = data.get("password")

    if username == "test" and password == "test":
        response = {"success": True}
    else:
        response = {"success": False}

    resp = make_response(jsonify(response))
    resp.headers["Access-Control-Allow-Origin"] = "http://127.0.0.1:5500"
    return resp

if __name__ == "__main__":
    # context = ssl.SSLContext(ssl.PROTOCOL_TLSv1_2)
    # context.load_cert_chain('cert.pem', 'key.pem')
    app.run(host="127.0.0.1", port=5550)