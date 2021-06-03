import os
import flask
from flask import Flask


def create_app(test_config=None):
    # create and configure the app
    app = Flask(__name__, instance_relative_config=True)
    # a simple page that says hello

    @app.route('/')
    def hello():
        return 'Hello, World!'

    return app


if __name__ == '__main__':

    # Create app.
    app = create_app()

    # Run app. For production use another web server.
    # Set debug and use_reloader parameters as False.
    app.run(port=5000, debug=True, host='0.0.0.0', use_reloader=True)
