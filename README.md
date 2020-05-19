# Mobil Programlamaya Giriş Ödev 2 ve Ödev 3

## Ödev 3 için Hazırladığım Videonun Linki

https://youtu.be/CF3RLShAqSA

## Ödev 3 için Eklenen Kısımlar

**1. Gelen Arama ve Mesajları Log'lamak**

Bu kısım için bir aktivite oluşturulmamıştır. Sadece gelen arama ve SMS'ler loglanmaktadır. Giden arama ve SMS'ler için işlem yapılmamaktadır.

Gelen Arama ve SMS:
<p align="center">
  <img src="Screenshots/aramalar.png" width="50%">
</p>

**2. AsyncTask'lar kullanılarak Progress Bar Üzerinden Hayali İndirme Yapma**

AsycnTask ile random sayı üretilmiştir. Üretilen bu sayılar Progress Bar'a eklenmektedir ve yüzde dolunca ses çalarak görüntü gösterilmektedir.

<p align="center">
  <img width="30%" src="Screenshots/indirme.png">
</p>

**3. Alarm Oluşturma ve İptal Etme**

TimePicker ile saat seçilmektedir. BroadcastReceiver ile seçilen bu saatte AlarmManager kullanılarak Vibration ve ses ile Notification gönderilmektedir. Oluşturulan alarm iptal edilebilmektedir.

<p align="center">
  <img src="Screenshots/alarm.png" width="50%">
</p>

**4. Son Konumu Alma ve Whatsapp Aracılığıyla Gönderme**

Cihazın son konumu FusedLocationProviderClient üzerinden çekilmektedir. Latitude ve longitude değerlerinden bir maps linki oluşturulmakta ve bu link Whatsapp aracılığıyla gönderilmektedir.

<p align="center">
<img src="Screenshots/location.jpg" width="30%">.
</p>



