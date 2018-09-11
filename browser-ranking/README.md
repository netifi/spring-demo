# proteus-demo
Demo application for [Netifi Proteus](https://www.netifi.com) and [RSocket](http://rsocket.io).

## Preparing the Demo

- Ensure you have Yarn, Node, and NPM installed:
    - https://nodejs.org/en/download/
    - https://www.npmjs.com/get-npm
    - https://yarnpkg.com/lang/en/docs/install/

## Running the Demo

- Fork/clone the repo and `cd` to the root directory from a terminal window
- Install the JavaScript dependencies with

    yarn

- The generated code in `src/main/resources/web/public/` should be
  checked in, and can be updated with:

    yarn run build

- To view the app in action, start a web server to host the JS:

    yarn start

- Hit http://localhost:3000 in a webbrowser

- The page will have several sections

    - My ID for this session is 
        - spits out a randomish string that will identify this copy of the application in messages
    
    - Available Brokers
        - Gives a series of messages that identify available Broker instances
    
    - Different Interaction Types
        - Each interaction type will print 2 scrolling lists of messages, those prefixed with "CLIENT" when the application is acting as a client, and messages prefixed with "SERVICE" when it is servicing a request of that interaction type
            - Request/Responses
            - Fire and Forgets
            - Streams

- Shortly after loading the page, CLIENT and SERVER messages should start appearing. The default behavior runs through the different Ping Pong interactions on interval timers
- Each message should refer to an identifier like the one in "My ID for this session...", e.g. India-Four-Two-One
- With only one browser instance, all CLIENT and SERVICE messages will have your identifier in them. Unless someone else happens to be connected to the same Broker
- You can experience the magic by opening multiple tabs or multiple browsers (e.g. Chrome and Safari) and going to http://localhost:3000
    - You should notice that each instance now will sometimes to refer to other IDs. This is other application instances servicing your messages and vice versa
    
## Modifying the Demo

To edit the homepage (including the JavaScript example code), do the following:

- In a text editor of your choice, open and modify `src/main/js/index.js` and/or `src/main/resources/index.html`

- Regenerate the website code in `src/main/resources/web/public/`  with:

    yarn run build

- Launch the new version of the website, which delivers your modified app with:

    yarn start
    
- Hit http://localhost:3000 in a webbrowser

### Notes

- To add a new service/client, follow the model of the PingPong service
    - The _service_ must implement ReactiveSocket methods
        - `fireAndForget`, `requestResponse`, `requestStream`, `requestChannel`, `pushMetadata`
        - Thing to note here, `requestChannel` and `pushMetadata` are not implemented by the RSocketJS library, so these don't work at the moment
    - The _client_ should use the Proteus Gateway to create a "group" routed connection, as the PingPong client does
    - Register the new service with the Proteus Gateway with a unique name as the PingPong service does
    `proteus.addService('my.new.service.id', myNewServiceImplementation);`
  
## Bugs and Feedback
For bugs, questions, and discussions please use the [Github Issues](https://github.com/netifi-proteus/proteus-browser-demo/issues).

## License
Copyright 2018 [Netifi Inc.](https://www.netifi.com)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
