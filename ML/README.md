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
* Convert Model to TFLite

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

![image](https://user-images.githubusercontent.com/67249292/120629667-3f398100-c490-11eb-9680-e57a003da197.png)

![image](https://user-images.githubusercontent.com/67249292/120629731-51b3ba80-c490-11eb-9315-a85961f4b2d8.png)

        
# Prepare global variables for training
In this section we will determine how many epochs for the training process, input dimension, batch_size. etc
Input parameters for network
* dim = (150, 150)
* channel = (3, )
* input_shape = dim + channel
* batch_size = 16
* epoch = 10 & 20 

# Preparing the dataset pipeline and augmentation For the dataset creation process
The pipeline dataset is a command to extract data in the form of digital images/images, which come from a folder into an array that can be read by tensorflow, the function used is *from tensorflow.keras.preprocessing.image import ImageDataGenerator*

This function is an Image data generator so that we generate image data from a file / folder that we created earlier. in this section we can determine what kind of generator / augmentation can be done.

* Rescaling data to 1/255.
* Shearing image 0.2 . scale
* Zooming image with
* range 0.2 and do a Horizontal flip

# Creating Convolutional Neural Network Structure
After creating the generator dataset, then we create a neural network. We use transfer learning from Tensorflow Keras. in this project we use Xception for related papers can be read [Xception: Deep Learning with Depthwise Separable Convolutions (CVPR 2017)](https://arxiv.org/abs/1610.02357)
![model1](https://user-images.githubusercontent.com/67249292/120634513-89713100-c495-11eb-8d5d-509cb029aa4d.png)


# Compile model
* Optimizer = is the optimization method used.
* loss = is a method of measuring the loss value based on what value. because Making the data flow we use categorical so that in this loss value we also use categorical loss.
* Metrics = The matrix value measured in this project we use the accuracy value as the measurement value.


