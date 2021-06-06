# Machine Learning Home Agriculture Monitoring System
We'll use TensorFlow 2 to create an image classification model, train it with a leaf disease dataset, and convert it to TensorFlow Lite using post-training quantization).

The model is based on a pre-trained version of MobileNet V2. We'll start by retraining only the classification layers, reusing MobileNet's pre-trained feature extractor layers. Then we'll fine-tune the model by updating weights in some of the feature extractor layers. This type of transfer learning is much faster than training the entire model from scratch.

Once it's trained, we'll use post-training quantization to convert all parameters to int8 format, which reduces the model size and increases inferencing speed. This format is also required for compatibility on the TFlite in android.

# Notebook ML 
[Fix notebook](https://github.com/maulanaakbardj/Home-Agriculture-Monitoring-System/blob/main/ML/notebook/leaf_classification_fix.ipynb)

# Import the required libraries :
**Note**: This notebook requires TensorFlow 2.3+ for full quantization, which currently does not work for all types of models. In particular, this notebook expects a Keras-built model and this conversion strategy currently doesn't work with models imported from a frozen graph. 

* Tensorflow 
* Numpy

In order to quantize both the input and output tensors, we need TFLiteConverter APIs that are available in TensorFlow r2.3 or higher:

# Prepare the training data

* First let's download and organize the leaf disease dataset we'll use to retrain the model. Here we use an example of a leaf disease dataset image from Kaggle ( [plantdisease](https://www.kaggle.com/emmarex/plantdisease), [new-plant-diseases-dataset](https://www.kaggle.com/vipoooool/new-plant-diseases-dataset),[rice-leaf-diseases](https://www.kaggle.com/vbookshelf/rice-leaf-diseases) ) 
 
 * Of several datasets combined into one [dataset](https://github.com/maulanaakbardj/Home-Agriculture-Monitoring-System/tree/main/ML/Data)
 
 * We use ImageDataGenerator to rescale the image data into float values (divide by 255 so the tensor values are between 0 and 1), and call flow_from_directory() to create two generators: one for the training dataset and one for the validation dataset.
 
 * On each iteration, these generators provide a batch of images by reading images from disk and processing them to the proper tensor size (224 x 224). The output is a tuple of (images, labels).
 
 * Save the class labels to a text file.
  
# Build the model

We'll create a model that's capable of transfer learning on just the last fully-connected layer.

We'll start with [MobileNet V2](https://www.tensorflow.org/api_docs/python/tf/keras/applications/mobilenet_v2) from Keras as the base model, which is pre-trained with the ImageNet dataset (trained to recognize 1,000 classes). This provides us a great feature extractor for image classification and we can then train a new classification layer with our  dataset. 

**note** : Paper [MobileNetV2: Inverted Residuals and Linear Bottlenecks](https://arxiv.org/abs/1801.04381)

# Create the base model

When instantiating the [MobileNet V2](https://www.tensorflow.org/api_docs/python/tf/keras/applications/mobilenet_v2), we specify the include_top=False argument in order to load the network without the classification layers at the top. Then we set trainable false to freeze all the weights in the base model. This effectively converts the model into a feature extractor because all the pre-trained weights and biases are preserved in the lower layers when we begin training for our classification head.

# Add a classification head

Now we create a new [Sequential](https://www.tensorflow.org/api_docs/python/tf/keras/Sequential) model and pass the frozen [MobileNet V2](https://www.tensorflow.org/api_docs/python/tf/keras/applications/mobilenet_v2) as the base of the graph, and append new classification layers so we can set the final output dimension to match the number of classes in our dataset.

# Configure the model :

Although this method is called compile(), it's basically a configuration step that's required before we can start training. And because the majority of the model graph is frozen in the base model, weights from only the last convolution and dense layers are trainable.

# Train the model

Now we can train the model using data provided by the train_generator and val_generator that we created at the beginning.
![image](https://user-images.githubusercontent.com/67249292/120916823-f07d2880-c6d5-11eb-8635-c5323aca9741.png)

# Fine tune the base model

So far, we've only trained the classification layers—the weights of the pre-trained network were not changed.

One way we can increase the accuracy is to train (or "fine-tune") more layers from the pre-trained model. That is, we'll un-freeze some layers from the base model and adjust those weights (which were originally trained with 1,000 ImageNet classes) so they're better tuned for features found in our flowers dataset.

# Un-freeze more layers
So instead of freezing the entire base model, we'll freeze individual layers.

# Reconfigure the model
Now configure the model again, but this time with a lower learning rate (the default is 0.001).

# Continue training
Now let's fine-tune all trainable layers. This starts with the weights we already trained in the classification layers, so we don't need as many epochs.

Our model better, but it's not ideal.

The validation loss is still higher than the training loss, so there could be some overfitting during training. The overfitting might also be because the new training set is relatively small with less intra-class variance, compared to the original ImageNet dataset used to train [MobileNet V2](https://www.tensorflow.org/api_docs/python/tf/keras/applications/mobilenet_v2).

So this model isn't trained to an accuracy that's production ready, but it works well enough as a demonstration.

# Save all the models that have been trained
You can check here to see all the models that have been built [model_project](https://github.com/maulanaakbardj/Home-Agriculture-Monitoring-System/tree/main/ML/model_project)

# Convert Model to TFLite
Ordinarily, creating a TensorFlow Lite model is just a few lines of code with [TFLiteConverter](https://www.tensorflow.org/api_docs/python/tf/lite/TFLiteConverter). For example, this creates a basic (un-quantized) TensorFlow Lite model.

However, this .tflite file still uses floating-point values for the parameter data, and we need to fully quantize the model to int8 format.

To fully quantize the model, we need to perform [post-training quantization](https://www.tensorflow.org/lite/performance/post_training_quantization) with a representative dataset, which requires a few more arguments for the TFLiteConverter, and a function that builds a dataset that's representative of the training dataset.

You can check here to see [result TFLite](https://github.com/maulanaakbardj/Home-Agriculture-Monitoring-System/tree/main/ML/TFLite)

# Compare the accuracy
We have a fully quantized TensorFlow Lite model. To be sure the conversion went well, so we compare it.

But again, these results are not ideal but better for prototype.

# Creating an example deploy Production Function using Flask Python
You can check here to see deploy with [Flask](https://github.com/maulanaakbardj/Home-Agriculture-Monitoring-System/tree/main/ML/Flask/Deploy%20Test)


