const {
    Proteus,
} = require('proteus-js-client');


const {
    Single
} = require('rsocket-flowable');

const {
    RankingServiceServer
} = require('./proto/service_rsocket_pb')

const {
    RankingResponse
} = require('./proto/service_pb')


/** Helpers **/

// Convenience method to update the webpage as new messages are available
function addMessage(message, element) {
    var ul = document.getElementById(element);
    var li = document.createElement("li");
    li.appendChild(document.createTextNode(message));
    if(ul.childElementCount >= 10){
        ul.removeChild(ul.childNodes[0]);
    }
    ul.appendChild(li);
}

function main() {
    const url = __WS_URL__;

    // This Proteus object acts as our gateway to both send messages to services and to register services that we support
    const proteus = Proteus.create({
        setup: {
            group: 'springone.demo.ranking',
            accessKey: 9007199254740991,
            accessToken: 'kTBDVtfRBO4tHOnZzSyY5ym2kfY=',
        },
        transport: {
            url,
        }
    });


    const handler = {
        rank: function(rankingRequest, metadata){
            rankingRequest.getRecordsList().forEach(record => {
                console.log(record.getId() + "  " + record.getData().getSupername());
            });
            console.log("....");
            let resp = new RankingResponse();
            resp.setId(rankingRequest.getRecordsList()[0].getId());
            return Single.of(resp);
        }
    };

    proteus.addService('io.rsocket.springone.demo.RankingService', new RankingServiceServer(handler));

    proteus._connect().subscribe({
        onComplete: function onComplete(connection) {
            console.log("connected")
        },
        onError: function onError(err) {
            console.log(err)
        },
        onSubscribe: function onSubscribe(cancel) {
            /*not sure we would ever cancel*/
        }
    });

}

document.addEventListener('DOMContentLoaded', main);
