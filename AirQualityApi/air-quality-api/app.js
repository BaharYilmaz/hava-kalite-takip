const express = require('express')
const app= express();
const morgan = require('morgan');//Sunucu içerisine gelen logları tutar ve yazdırır
const bodyParser = require('body-parser');//body ile gelen verileri parse etmemizi sağlar
const mongoose = require('mongoose');//MongoDb Orm aracı
const userRouter = require('./router/user')


mongoose.connect('mongodb+srv://mts:'+process.env.MONGO_ATLAS_PW+'@mts-gpqz8.gcp.mongodb.net/test?retryWrites=true&w=majority',
{useNewUrlParser: true,useUnifiedTopology: true}
);//Mongoose bağlantısını verdiğiniz connet metodu


app.use(morgan('dev'));

app.use(bodyParser.urlencoded({extended:false}))//encode edilmiş bir url de çalışacaksak bunu true yapmalıyız
app.use(bodyParser.json())//Gelen json datayı kullanabilmek için  ekliyoruz
app.use('/upload',express.static('upload'))//Dosyaya erişim hakkı veriyoruz

app.use((req,res,next)=>//Cors entegrasyonu
{
    res.header("Access-Control-Allow-Origin","*");
    res.header("Acces-Controls-Allow-Headers","Origin,X-Requested-With,Content-Type,Accept,Authorization");
    if(req.method==='OPTIONS')
    {
        res.header("Acces-Controls-Allow-Methods","PUT,POST,DELETE,PATCH");
        return res.status(200).json({});
    }
    next();
})



app.use('/user',userRouter);
app.use((req,res,next)=>{//bulunamayan sayfa hatası
    const error = new Error("not found");
    error.status=404;
    next(error);
});

app.use((error,req,res,next)=>
{
     res.status(error.status || 500)
     res.json({
         error : {
             message : error.message
         }
     }) 
})

module.exports =app;