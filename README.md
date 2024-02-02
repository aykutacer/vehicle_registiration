# vehicle_registiration
Projede temel olarak araç kayıtlarını ve kullanıcı araç yetkilendirmelerini tutabileceğimiz bir mikro servis geliştirilmesi.
# Araç Kayıtları
Bu mikroservis kapsamında araç bilgilerini saklamak, organize etmek ve kullanıcıları araçlara
yetkilendirmek istiyoruz. Araçlar için plaka, şase numarası, etiket (serbest metin), marka,
model, model yılı bilgileri tutuyoruz. Plaka, marka, model ve model yılı bilgileri zorunlu
alanlarımız olmalıdır, diğer alanlar ise seçimli alanlar olmalıdır.
Elbette backendin vazgeçilmezi araç için CRUD servisleri olması gerekiyor. Önemli nokta
"CUD" kısmını sadece Admin kullanıcısı yapabiliyor iken "R" kısmını herkes
yapabilmelidir.
# Gruplama
Mikro servisimize araçları gruplama özelliği ekleyeceğiz. Bir grup altında araç ve/veya grup bulundurabilir. İç içe gruplar ve
araçlar tanımladığımızda bir araç ağacımız olacak.

Bunların hepsi için bir Authentication mekanizması olacak.
