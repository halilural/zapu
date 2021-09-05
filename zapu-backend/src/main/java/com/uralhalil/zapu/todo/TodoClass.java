package com.uralhalil.zapu.todo;

/**
 * Task Açıklaması
 * zapu.com a hoşgeldiniz. zapu.com tapu.com'un mimarisinin modern yaklaşımlarla klonlanmış halidir. zapu.com kullanıcılarına gayrimenkul alışverişinde
 * şeffaf ve güvenli bir deneyim sunmayı amaçlamaktadır.
 * <p>
 * zapu.com'a ilanlar api aracılığı ile yüklenir.
 * <p>
 * zapu.com/api/categories endpointi sisteme gayrimenkul yüklemek isteyen kullanıcılar için seçimi zorunlu olan kategorileri döner
 * [{"id":1, "name":"konut"}, {"id":2, "name":"ticari"}, {"id":3, "name":"arsa"}]
 * <p>
 * zapu.com/api/cities endpointi sisteme gayrimenkul yüklemek isteyen kullanıcılar için seçimi zorunlu olan şehir bilgilerini döner
 * [{"id":1, "name":"Antalya"}, {"id":2, "name":"İstanbul"}, {"id":3, "name":"Ankara"}, ....]
 * <p>
 * zapu.com/api/property endpointi aracılığı ile sisteme gayrimenkul yüklemek isteyen kullanıcılar ilan girişi ve güncellemesi yapar.
 * <p>
 * {
 * "category": 1,
 * "title": "Ankara Yeni mahalle 2+1 daire",
 * "city":3,
 * "price": 600000,
 * "currency": "TL"
 * ....
 * }
 * <p>
 * <p>
 * zapu.com/detay/{id} url i sistemdeki bir gayrimenul detayını görebildiğimiz front office url yapısıdır. Url yapısındaki id bilgisi kullanılarak
 * son kullanıcı ve arama motorlarının ihtiyaçlarını karşılayacak şekilde hazırlar ve hızlıca cevap döner.
 * Performance detay sayfaları için en yüksek önceliklerden biridir. Detay url i zaman zaman yoğun yük altında kalabilmektedir.
 * <p>
 * {
 * "category": 1,
 * "title": "Ankara Yeni mahalle 2+1 daire",
 * "city":Ankara,
 * "price": 600000,
 * "currency": "TL"
 * ....
 * }
 * <p>
 * <p>
 * zapu.com kullanıcıları ve arama motorları için user friendly url yapısında çalışır. SEO optimizasyonu için çok önemli olan root url
 * yapısına arama sayfaları oluşturulurken çok dikkat edilmelidir. Eğer kategori, lokasyon bilgileri kullanılarak user friendly root url oluşturulabiliyorsa
 * arama sayfası 301 ile user friendly url yapısına yönlendirilmelidir. Aşağıda bazı arama davranışları, urlleri ve beklenen response ları örneklenmiştir.
 * Arama url i zaman zaman yoğun yük altında kalabilmektedir.
 * <p>
 * zapu.com/arama?category=1 => 301 zapu.com/konut
 * {
 * "page":1,
 * "pageSize":5,
 * "totalPage":10,
 * "count": 5,
 * "result": [{"id" : 1, "title": "Ankara Yeni mahalle 2+1 daire", "price":10000, currency:$},
 * {"id" : 2, "title": "İstanbul Ümraniye 2+1 daire", "price":90000, currency:TL} ....],
 * "rootUrl": "zapu.com/konut"
 * }
 * <p>
 * zapu.com/arama?category=1&city=34 => 301 zapu.com/konut/istanbul
 * {
 * "page":1,
 * "pageSize":5,
 * "totalPage":10,
 * "count": 5,
 * "result": [{"id" : 1, "title": "İstanbul Etiler 2+1 daire", "price":20000, currency:$},
 * {"id" : 2, "title": "İstanbul Ümraniye 2+1 daire", "price":90000, currency:TL} ....],
 * "rootUrl": "zapu.com/konut/istanbul"
 * }
 * <p>
 * zapu.com/arama?category=1&city=34&page=2 => 301 zapu.com/konut/istanbul?page=2
 * {
 * "page":2,
 * "pageSize":5,
 * "totalPage":10,
 * "count": 5,
 * "result": [{"id" : 1, "title": "İstanbul Etiler 2+1 daire", "price":20000, currency:$},
 * {"id" : 2, "title": "İstanbul Ümraniye 2+1 daire", "price":90000, currency:TL} ....],
 * "rootUrl": "zapu.com/konut/istanbul"
 * }
 * <p>
 * zapu.com/arama?category=1&city=34&city=3
 * {
 * "page":1,
 * "pageSize":5,
 * "totalPage":15,
 * "count": 5,
 * "result": [{"id" : 1, "title": "İstanbul Etiler 2+1 daire", "price":20000, currency:$},
 * {"id" : 2, "title": "Afyon 3+1 daire", "price":90000, currency:TL} ....],
 * "rootUrl": "zapu.com/arama?category=1&city=34&city=3"
 * <p>
 * <p>
 * }
 * <p>
 * Arama sayfaları url yapısı geliştirilirken ilerde ilçe, mahalle gibi yeni lokasyon kırılımlarından en az nasıl etkileneceği düşünülmelidir
 * ve sistem ona göre kurulmalıdır.
 * <p>
 * zapu.com sistemi java dilinde ve spring boot kullanılarak geliştirilmelidir. Yoğun yük altında çalışan sistemimiz kolayca scale edilebilmeli
 * ve son kullanılar deploymentlardan etkilenmemelidir. Database ve diğer teknoloji seçimleri developerin tercihine bırakılmıştır.
 * <p>
 * Sistem AWS üzerinde kolayca deploy edilebilmelidir. Sistemimiz kesintisiz hizmet verebilmeli, yoğun yük taleplerine otomatik
 * cevap verebilmeli ve fiyat optimizasyonunu her zaman ön planda tutulmalıdır.
 * <p>
 * Temel uygulama geliştirme yetkinlikleri dışında aşağıdaki konu başlıkları proje değerlendirme aşamasında göz önünde bulundurulacaktır.
 * Uygulama local environment üzerinde test edilebilir olmalıdır.
 * AWS üzerinde hangi servisleri kullanarak nasıl bir çözüm oluşturulacağı mimari olarak açıklanmalıdır.
 * <p>
 * ** Terraform ile IaC
 * ** AWS know how
 * ** Docker
 * ** Docker-compose
 * ** Integration tests
 * ** Unit test coverage
 * ** Design patterns
 * ** Clean Code
 * ** Performance, scalability, and High availability
 * ** CI/CD
 * ** Deploy to prod environment
 */
public class TodoClass {
    /**
     * Case Study
     *  Category Service done!
     *  City Service done!
     *  Property Service done! (needs to write unit and integration tests)
     *  Property search service will be developed, hence rootUrl will be produced effectively using search terms.
     *  The app must be scalable and run without any cut to not affect end-users.
     *  AWS Service Infrastructure have to be explained if you cant have enough time to develop it.
     *  (aws infra consist of elastic auto scaling, elastic load balancing, ec2 instances e.t.c)
     *  During development aws infra, you can use the Terraform or aws CloudFlare to develop it.
     */
}
