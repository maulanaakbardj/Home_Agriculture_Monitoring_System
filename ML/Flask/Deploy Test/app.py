from keras.preprocessing.image import img_to_array
from keras.models import load_model
import numpy as np
import imutils
import pickle
import cv2
from flask import Flask, request, jsonify, render_template
import requests
from io import BytesIO

from PIL import Image


app = Flask(__name__)

# Parameters
input_size = (150,150)

#define input shape
channel = (3,)
input_shape = input_size + channel

#define labels
labels = ['Bercak Bakteri', 'Bercak Daun Abu-Abu', 'Bercak Daun Coklat', 'Bercak Daun Target Spot', 'Bercak Kering Alternaria', 'Bintik daun Septoria', 'Busuk Hitam', 'Esca (Campak hitam)', 'Hangus Daun', 'Hawar Daun', 'Hawar Daun (Bercak Daun Isariopsis)', 'Hawar Daun Bakteri', 'Jamur tepung', 'Kapang Daun', 'Karat Apel Cedar', 'Karat Daun', 'Keropeng Apel', 'Sehat', 'Smut Daun', 'Tungau Laba laba Spider Mite', 'Virus Kuning Keriting', 'Virus Mosaik']
MODEL_PATH='models/Model_Daun_Xception.h5'
model = load_model(MODEL_PATH,compile=False)
def preprocess(img,input_size):
    img = Image.fromarray(img)
    nimg = img.convert('RGB').resize(input_size, resample= 0)
    img_arr = (np.array(nimg))/255
    return img_arr

def reshape(imgs_arr):
    return np.stack(imgs_arr, axis=0)
    
@app.route('/')
def index():
	return render_template("index.html", data="hey")


@app.route("/prediction", methods=["POST"])
def prediction():

	img = request.files['img']

	img.save("img.jpg")

	image = cv2.imread("img.jpg")

	image = preprocess(image, input_size)

	image = reshape([image])

	y = model.predict(image)

	pred = np.argmax(y); pre=np.max(y)*100;pred2 = sorted(list(set(y.flatten().tolist())))[-2]*100

	label = labels[pred]; labele = labels[int(pred2)]

	return render_template("prediction.html", data=label, data2=pre, data3=labele,data4=pred2)


if __name__ == "__main__":
	app.run(debug=True)
