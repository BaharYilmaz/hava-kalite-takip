const express = require('express');
const router = express.Router();
const mongoose = require('mongoose');
const multer = require('multer');
const Complaint = require('../model/complaint');
const ComplaintController = require('../controller/complaint')

const Storage = multer.diskStorage({//dis içerisine kaydedilişini bu şekilde yapabiliriz
    destination: function (req, file, cb) {
        cb(null, "./upload/");
    },
    filename: function (req, file, cb) {
        cb(null, file.originalname);
    }
})
const upload = multer({ storage: Storage })//file filter özelliği ile gerekli düzenlemeler yapılabilir dökümana bak



router.post('/', upload.single('complaintImage'), (req, res, next) => {
    console.log("girdi");
    console.log(req.file)
    const complaint = new Complaint({
        _id: mongoose.Types.ObjectId(),
        name: req.body.name,
        email: req.body.email,
        photoPath: req.file.path,
        adress: req.body.adress

    });
    console.log('complaint ******* ' + complaint);
    complaint.save().then(result => {
        console.log(result);
        res.status(200).json({//response gönder
            message: "Handling Post request ",
            createdComplaint: {

                name: result.name,
                email: result.email,
                _id: result._id,
                photoPath: result.photoPath,
                adress: result.adress

            }
        });
    }).catch(err => {
        res.status(500).json({
            error: err
        });
    });
});

router.get('/',(req,res,next) =>{
Complaint.find().select("name email photoPath adress").exec().then(result =>{
    const response = {
        count : result.length,
        complaints : result.map(res =>{
            return {
                name : res.name,
                email : res.email,
                adress : res.adress,
                complaintImage : res.photoPath,
                request : {         
                    type : "GET",
                    Url : "localhost:4242/complaint/"+res._id
                }
            }
        })
    }
    res.status(200).json({
        response
       })
}).catch(err =>{
    res.status(404).json({
        error : err
    })

});



})
module.exports = router;