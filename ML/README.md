# Machine Learning Home Agriculture Monitoring System
leaf disease image classification, Using Keras-Tensorflow

# This directory will cover several things:

* Creating a Pipeline for the input dataset
* Building a Convolutional Neural Network Model,
* Using the existing CNN Model Architecture.
* Compile Model
* Model Training
* Model Evaluation
* Model Save
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

Example of Model Tomato leaf classification Visualization results :

![model1](https://user-images.githubusercontent.com/67249292/120635482-b96d0400-c496-11eb-80b7-d810949db504.png)

# Compile model
* Optimizer = is the optimization method used.
* loss = is a method of measuring the loss value based on what value. because Making the data flow we use categorical so that in this loss value we also use categorical loss.
* Metrics = The matrix value measured in this project we use the accuracy value as the measurement value.

# Model Tomato leaf classification training process :

![image](https://user-images.githubusercontent.com/67249292/120635149-554a4000-c496-11eb-8474-657716dbb722.png)

And the results of the training are like that, the results are apparently not optimal but enough for this experiment, it turns out to be for this model. Where :

![image](https://user-images.githubusercontent.com/67249292/120635987-4a43df80-c497-11eb-95cc-63b23a35a5a4.png)

![image](https://user-images.githubusercontent.com/67249292/120636020-56c83800-c497-11eb-85ab-01e9086f2e49.png)

You can check here to see all the notebooks that have been built [notebook Build ML](https://github.com/maulanaakbardj/Home-Agriculture-Monitoring-System/tree/main/ML/notebook/BuildML)

# Model Tomato leaf classification Evaluation

Accuracy on training data: 0.9838 

Loss on training data: 0.0629 

Accuracy on test data: 0.9875 

Loss on test data: 0.0487 

# Save all the models that have been trained
You can check here to see all the models that have been built [model_project](https://github.com/maulanaakbardj/Home-Agriculture-Monitoring-System/tree/main/ML/model_project)

# Creating an example deploy Production Function using Flask Python
You can check here to see deploy with [Flask](https://github.com/maulanaakbardj/Home-Agriculture-Monitoring-System/tree/main/ML/Flask/Deploy%20Test)
