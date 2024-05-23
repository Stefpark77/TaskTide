from http.server import BaseHTTPRequestHandler, HTTPServer
import json
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.svm import SVC
import joblib

# Load the trained classifier
classifier = joblib.load('classifier_model.pkl')

# Load the TF-IDF vectorizer
vectorizer = joblib.load('vectorizer.pkl')

class RequestHandler(BaseHTTPRequestHandler):
    def do_POST(self):
        content_length = int(self.headers['Content-Length'])
        post_data = self.rfile.read(content_length)

        # Parse JSON data
        request_data = json.loads(post_data.decode('utf-8'))

        # Get the text from the request
        text = request_data.get('text', '')

        # Lemmatize the text (You may need to define your lemmatization function)
        # lemmatized_text = lemmatize_text(text)

        # Vectorize the text
        text_vectorized = vectorizer.transform([text])

        # Estimate the difficulty
        difficulty = classifier.predict(text_vectorized)[0]

        # Send response
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