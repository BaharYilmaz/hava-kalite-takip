<h1 align="center">Hava Kalite Takip Uygulaması</h1>


> Uygulama, kullanıcınun bulundupu konumdaki havanın kalitesini takip etmesini, hava kirliliğine neden olan durumları uygulama üzerinden yetkililere bildirmesini amaçlamaktadır. Uygulamaya aile üyeleri eklenebilmekte ve bu üyelerin bulundukları şehirlerdeki hava kalitesi takip edilebilmektedir.

## Demo



## Kurulum

```
yarn install

```
## Kurulum gRPC Server Side

From the `hava_kalite_takip/AirQualityApi/air-quality-api/grpcServer/proto/Server/` directory:

 1. Run the npm install:

    ```sh
    $ npm install
    ```
 2. Run the npm install for gRPC package:

    ```sh
    $ npm i grpc    

    $ npm i @grpc/proto-loader
    ```

 2. From another terminal, run the client:

    ```sh
    $ node server.js
    ```
## Kurulum NodeJs Server Side

From the `hava_kalite_takip/AirQualityApi/air-quality-api/` directory:

 1. Run the npm install:

    ```sh
    $ npm install
    ```
 2. From another terminal, run the client:

    ```sh
    $ node server.js
    ```
> Expected Output

<img width="1679" alt="Test" src="https://user-images.githubusercontent.com/31216880/80509145-a88fbc00-8981-11ea-8c93-f01e773a7f20.png">

## Kullanılan Teknolojiler

* Google Maps Api
* Air Quality Index Api
* Android Studio 3.6
* Java 1.8
* Node.js 
* MongoDB 
* Firebase Cloud Storage
* gRPC

