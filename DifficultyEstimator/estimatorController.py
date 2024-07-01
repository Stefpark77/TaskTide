from http.server import BaseHTTPRequestHandler, HTTPServer
import json
import joblib

from trainModel import lemmatize_text

classifier = joblib.load('classifier_model.pkl')
vectorizer = joblib.load('vectorizer.pkl')


class RequestHandler(BaseHTTPRequestHandler):
    def do_POST(self):
        content_length = int(self.headers['Content-Length'])
        post_data = self.rfile.read(content_length)
        request_data = json.loads(post_data.decode('utf-8'))

        text = request_data.get('text', '')
        lemmatized_text = lemmatize_text(text)
        text_vectorized = vectorizer.transform([lemmatized_text])
        difficulty = classifier.predict(text_vectorized)[0]

        self.send_response(200)
        self.send_header('Content-type', 'application/json')
        self.end_headers()
        self.wfile.write(json.dumps({'difficulty': difficulty}).encode('utf-8'))


def run(server_class=HTTPServer, handler_class=RequestHandler, port=8000):
    server_address = ('', port)
    httpd = server_class(server_address, handler_class)
    print(f'Starting server on port {port}...')
    httpd.serve_forever()


if __name__ == '__main__':
    run()
