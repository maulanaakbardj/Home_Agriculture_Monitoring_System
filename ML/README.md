# Machine Learning Home Agriculture Monitoring System
leaf disease image classification, Using Keras-Tensorflow

# This directory will cover several things:

* Creating a Pipeline for the input dataset
* Building a Convolutional Neural Network Model,
* Using the existing CNN Model Architecture.
* Compile Model
* Model Training
* Model Save
* Model Evaluation
* Create Predict Functions.
* Test Deploy Model With Flask (Python)

# Libraries used :

* Tensorflow 
* Numpy
* Scikit-learn
* PILLOW for image library

# Preparing the Data-set
prepare the dataset first. Here we use an example of a leaf disease image from Kaggle, so that it is convenient for the data, .

![image](https://user-images.githubusercontent.com/67249292/120629468-09949800-c490-11eb-91cb-05389789c8b0.png)
        
In this first process, we want to divide the data into 3 parts. namely train, test, and validation. with the proportion (80,10,10).
Then, in this process we want to split the data for each directories into something like this.

 └── Data
        ├── Anggur
        │   ├── Busuk Hitam
        │   ├── Esca (Campak hitam)
        │   ├── Hawar Daun (Bercak Daun Isariopsis)
        │   ├── Sehat
        │   └── dataset
        │       ├── train
        │             ├── ...      
        │       ├── validation
        │             ├── ...   
        │       └── test
        │             └── ...   
        ├── Apel
        │   ├── Busuk Hitam
        │   ├── Karat Apel Cedar
        │   ├── Keropeng Apel
        │   ├── Sehat
        │   └── dataset
        │       ├── train
        │             ├── ...      
        │       ├── validation
        │             ├── ...   
        │       └── test
        │             └── ...   
        ├── Daun
        │   ├── Bercak Bakteri
        │   ├── Bercak Daun Abu-Abu
        │   ├── Bercak Daun Coklat
        │   ├── Bercak Daun Target Spot
        │   ├── Bercak Kering Alternaria
        │   ├── Bintik daun Septoria
        │   ├── Busuk Hitam
        │   ├── Esca (Campak hitam)
        │   ├── Hangus Daun
        │   ├── Hawar Daun (Bercak Daun Isariopsis)
        │   ├── Hawar Daun Bakteri
        │   ├── Hawar Daun
        │   ├── Jamur tepung
        │   ├── Kapang Daun
        │   ├── Karat Apel Cedar
        │   ├── Karat Daun
        │   ├── Keropeng Apel
        │   ├── Sehat
        │   ├── Smut Daun
        │   ├── Tungau Laba laba Spider Mite
        │   ├──  Virus Kuning Keriting
        │   └── dataset
        │       ├── train
        │             ├── ...      
        │       ├── validation
        │             ├── ...   
        │       └── test
        │             └── ...   
        ├── Jagung
        │   ├── Bercak Daun Abu-Abu
        │   ├── Hawar Daun
        │   ├── Karat Daun
        │   └── Sehat
        │   └── dataset
        │       ├── train
        │             ├── ...      
        │       ├── validation
        │             ├── ...   
        │       └── test
        │             └── ...  
        ├── Kentang
        │   ├── Bercak Kering Alternaria
        │   ├── Hawar Daun
        │   └── Sehat
        │   └── dataset
        │       ├── train
        │             ├── ...      
        │       ├── validation
        │             ├── ...   
        │       └── test
        │             └── ...  
        ├── Padi
        │   ├── Bercak Daun Coklat
        │   ├── Hawar Daun Bakteri
        │   └── Sehat
        │   └── dataset
        │       ├── train
        │             ├── ...      
        │       ├── validation
        │             ├── ...   
        │       └── test
        │             └── ...  
        └── Tomat
        │   ├── Bercak Bakteri
        │   ├── Bercak Daun Target Spot
        │   ├── Bercak Kering Alternaria
        │   ├── Bintik daun Septoria
        │   ├── Hawar Daun
        │   ├── Kapang Daun
        │   ├── Sehat
        │   ├── Tungau Laba laba Spider Mite
        │   ├── Virus Kuning Keriting
        │   ├──Virus Mosaik
        │   └── dataset
        │       ├── train
        │             ├── ...      
        │       ├── validation
        │             ├── ...   
        │       └── test
        │             └── ...  
        
# Prepare global variables for training
In this section we will determine how many epochs for the training process, input dimension, batch_size. etc
Input parameters for network
dim = (150, 150)
channel = (3, )
input_shape = dim + channel
batch_size = 16
epoch = 10 & 20

# Preparing the dataset pipeline and augmentation For the dataset creation process
The pipeline dataset is a command to extract data in the form of digital images/images, which come from a folder into an array that can be read by tensorflow, the function used is *from tensorflow.keras.preprocessing.image import ImageDataGenerator*

This function is an Image data generator so that we generate image data from a file / folder that we created earlier. in this section we can determine what kind of generator / augmentation can be done.

* Rescaling data to 1/255.
* Shearing image 0.2 . scale
* Zooming image with
* range 0.2 and do a Horizontal flip

# Creating Convolutional Neural Network Structure
After creating the generator dataset, then we create a neural network. We use transfer learning from Tensorflow Keras. in this project we use Xception for related papers can be read

# Compile model
* Optimizer = is the optimization method used.
* loss = is a method of measuring the loss value based on what value. because Making the data flow we use categorical so that in this loss value we also use categorical loss.
* Metrics = The matrix value measured in this project we use the accuracy value as the measurement value.


