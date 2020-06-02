const http = require('http')
const app = require("./app")
const server = http.createServer(app);


const port = process.env.port || 5000; //Port tanımını yaptık


const host = '0.0.0.0';
app.listen(port,host,()=>{
    console.log('Example app listening on port 4242.');

})
console.log("server listening");
  

