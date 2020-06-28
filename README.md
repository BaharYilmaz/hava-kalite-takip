<h1 align="center">Hava Kalite Takip Uygulaması</h1>


> Uygulama, kullanıcınun bulundupu konumdaki havanın kalitesini takip etmesini, hava kirliliğine neden olan durumları uygulama üzerinden yetkililere bildirmesini amaçlamaktadır. Uygulamaya aile üyeleri eklenebilmekte ve bu üyelerin bulundukları şehirlerdeki hava kalitesi takip edilebilmektedir.

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

## Ekran Görüntüleri

> Hava kalite takip ekranları

![c1](https://user-images.githubusercontent.com/48556212/85956815-0eec8700-b991-11ea-9de8-97db14aa1d8d.jpg)

> Rapor/şikayet ekranları

![c3(1)](https://user-images.githubusercontent.com/48556212/85956817-10b64a80-b991-11ea-8d64-1515a8d65cdc.jpg)

> Aile üyesi ekleme ve hava takip ekranları

![c2](https://user-images.githubusercontent.com/48556212/85956816-101db400-b991-11ea-92ca-e5f03e649f03.jpg)


## Kullanılan Teknolojiler

* Google Maps Api
* Air Quality Index Api
* Android Studio 3.6
* Java 1.8
* Node.js 
* MongoDB 
* Firebase Cloud Storage
* gRPC

