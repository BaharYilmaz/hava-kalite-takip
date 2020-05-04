const mongoose = require('mongoose');
const User = require('../model/user');
const bcrypt = require('bcrypt')
const jwt = require('jsonwebtoken')

exports.users_userLogin = (req,res,next) => {
    User.find({email : req.body.email}).exec().then(user =>{//kullanıcıyı db den çek
        if(user.length < 1){//böyle bi kullanıcı emali yoksa auth başarısız
            res.status(401).json({
                message : "Auth Failed"
            })
        }
        bcrypt.compare(req.body.password,user[0].password,(err,result)=>{//şifreyi db deki hashlenmiş şifre ile kontrol et
            if(err)
            {
              return  res.status(401).json({//hata gelirse auth failed
                    message : "Auth Failed"
                })
            }
            if(result)//sonuç dönerse başarılı giriş
            {
               const token =  jwt.sign({//token oluşturuyoruz
                    email : user[0].email,//payloada gömülen veriler
                    userId : user[0]._id

                },process.env.JWT_KEY,{
                    expiresIn:"5h"
                }
                )
                return res.status(200).json({
                    message :"auth succesfull",
                    token : token
                })
            }
            res.status(401).json({
                message : "Auth Failed"
            })
        })
    }).catch(err =>{
        res.status(500).json( {
          error : err
        });
    });
}

exports.users_userSignUp =  (req, res, next) => {

    User.find({email : req.body.email}).exec().then(user =>{
        if(user.length > 0) {
            res.status(422).json({
                message : "Mail exist"
            })
        }
        else{
            bcrypt.hash(req.body.password, 10, (err, hash) => {//pasword hash

                console.log("hashh"+hash)
                console.log(err)
                if (err) {
                    return res.status(500).json({
                        error: err
                    });
                }
                else {
                    const user = new User({
                        _id : mongoose.Types.ObjectId(),
                        email: req.body.email,
                        password: hash
                    })
                    user.save().then(result =>{
                        res.status(201).json({
                            message : "User is created"
                        })
                    }).catch(err =>{
                        res.status(500).json( {
                          error : err
                        });
                    });
                }
        
                
        
            })
        }
    })
   
};

exports.users_removeUser  =(req,res,next) =>{
    User.remove({
        _id : req.params.userId
    }).exec().then(result => {
        res.status(200).json({
            message :"Removed person",
            person : result
        });
    }).catch(error => {
        res.status(500).json({
            error : error
        });
    });
}