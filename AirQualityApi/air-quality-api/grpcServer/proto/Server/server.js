
var PROTO_PATH = __dirname + '/complaint.proto';
const mongoose = require('mongoose');
const Complaint = require('../../../model/complaint');
var grpc = require('grpc');
var protoLoader = require('@grpc/proto-loader');
var packageDefinition = protoLoader.loadSync(
    PROTO_PATH,
    {
        keepCase: true,
        longs: String,
        enums: String,
        defaults: true,
        oneofs: true
    });
var complaint_proto = grpc.loadPackageDefinition(packageDefinition).Complaint;
function CreateComplaint(call,callback){
    const complaint = new Complaint({
        _id: mongoose.Types.ObjectId(),
        name: call.request.name,
        email: call.request.email,
        imageUrl: call.request.imageUrl,
        adress: call.request.adress

    });

    complaint.save().then(result=>{
        callback(null, {status :result })
    }).catch(error => {
        callback(null, {status :error })
    })    
}

function main() {
    mongoose.connect('mongodb+srv://mts:PASSWORD@mts-gpqz8.gcp.mongodb.net/test?retryWrites=true&w=majority',
        { useNewUrlParser: true, useUnifiedTopology: true }
    )
    var server = new grpc.Server();
    server.addService(complaint_proto.ComplaintService.service,
        {

            CreateComplaint: CreateComplaint,
        });


    server.bind('0.0.0.0:50052', grpc.ServerCredentials.createInsecure());
    server.start();
    console.log("server starting")
}

main();