const http = require('http')
const app = require("./app")

const port = process.env.port || 4242; //Port tanımını yaptık

const server = http.createServer(app);

server.listen(port);
console.log("server listening");
  

