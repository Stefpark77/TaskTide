import pandas as pd
import nltk
from nltk.corpus import wordnet, stopwords
from nltk.stem import WordNetLemmatizer
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.model_selection import train_test_split
from sklearn.svm import SVC
from sklearn.metrics import accuracy_score, confusion_matrix, classification_report
import joblib

# NLTK resources
nltk.download('punkt')
nltk.download('averaged_perceptron_tagger')
nltk.download('wordnet')
nltk.download('stopwords')
stop_words = set(stopwords.words('english'))


def get_wordnet_pos(tag):
    if tag.startswith('J'):
        return wordnet.ADJ
    elif tag.startswith('V'):
        return wordnet.VERB
    elif tag.startswith('N'):
        return wordnet.NOUN
    elif tag.startswith('R'):
        return wordnet.ADV
    else:
        return wordnet.NOUN


# Lemmatization
def lemmatize_text(text):
    lemmatizer = WordNetLemmatizer()
    tokens = nltk.word_tokenize(text)
    pos_tags = nltk.pos_tag(tokens)
    lemmatized_tokens = [lemmatizer.lemmatize(token, get_wordnet_pos(tag)) for token, tag in pos_tags]
    filtered_sentences = [w for w in lemmatized_tokens if not w.lower() in stop_words]
    filtered_sentences2 = [w for w in filtered_sentences if not w.lower() in '.?!,']
    return ' '.join(filtered_sentences2)


df = pd.read_csv('trainData.csv', delimiter=';')
df['text_lemmatized'] = df['text'].apply(lemmatize_text)

# Splitting data (20% / 80%)
X_train, X_test, y_train, y_test = train_test_split(df['text_lemmatized'], df['difficulty'], test_size=0.2,
                                                    random_state=42)

# Vectorization ( TF-IDF )
vectorizer = TfidfVectorizer(max_features=1000)
X_train_vectorized = vectorizer.fit_transform(X_train)
X_test_vectorized = vectorizer.transform(X_test)

# Support Vector Classifier
classifier = SVC()
classifier.fit(X_train_vectorized, y_train)

# Evaluation:
y_pred = classifier.predict(X_test_vectorized)
accuracy = accuracy_score(y_test, y_pred)
print("Accuracy:", accuracy)
conf_matrix = confusion_matrix(y_test, y_pred)
print("Confusion Matrix:")
print(conf_matrix)
print("Classification Report:")
print(classification_report(y_test, y_pred))

# Specific Test
test_text = "Design an easy simple hello world program."
test_text_lemmatized = lemmatize_text(test_text)
test_text_vectorized = vectorizer.transform([test_text_lemmatized])
predicted_difficulty = classifier.predict(test_text_vectorized)
print("Predicted Difficulty:", predicted_difficulty[0])

# Save the trained model
joblib.dump(classifier, 'classifier_model.pkl')
joblib.dump(vectorizer, 'vectorizer.pkl')
