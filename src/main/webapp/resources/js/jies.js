var MENULER_VAR = 0; //hata ve görev menüleri ekranda gözüküyor

var DROPDOWNMENU_PROJE_ID = 1;
var DROPDOWNMENU_GOREV_ID = 2;
var DROPDOWNMENU_HATA_ID = 3;
var DROPDOWNMENU_PROJE_SECENEKLER_ID = 4;

var DROPDOWNMENU_PROJE_INDEX_PROJE_EKLE = 0;
var DROPDOWNMENU_PROJE_INDEX_BUTUN_PROJELER = 1;
var DROPDOWNMENU_PROJE_INDEX_SECILI_PROJE = -1;

var DROPDOWNMENU_SECENEK_OBJECT;//proje ismi değiştiğinde secenek menüsünün sıfırlanması için secenek menüsü nesnesi

var SECILI_PROJE_ADI = "";

var USTMENU_YUKSEKLIK = "100px";

var YAZI_SIFRE = "şifre";
var YAZI_KULLANICI_ADI = "kullanıcı adı";
var YAZI_PROJE_ADI = "proje adı";
var YAZI_PROJE_ACIKLAMA = "açıklama";
var YAZI_HATA_BASLIGI = "başlık";
var YAZI_HATA_ACIKLAMA = "açıklama";
var YAZI_GOREV_BASLIGI = "başlık";
var YAZI_GOREV_ACIKLAMA = "açıklama";

var CSS_ETKISIZ = "ontanimliEtkisizEleman";
var CSS_ETKIN = "ontanimliEtkinEleman";
var CSS_UST_MENU_2 = "ust_menu_2";
var CSS_YESIL_GOLGE = "yesilGolge";

var GIRIS_DIV = "#giris_div";
var GIRIS_TABLE = "#giris_table";
var GIRIS_BODY = "#giris_body";
var GIRIS_INPUTTEXT_KULLANICI_ADI = "giris_it_kullanici_adi";
var GIRIS_INPUTTEXT_SIFRE = "giris_it_sifre";
var GIRIS_OVERLAY = "#overlay";
var GIRIS_OVERLAY_BACK = "#overlay_back";
var GIRIS_DIV_SPINNER = "#spinner_div";

var SECENEK_DIV = "#secenek_div";
var SECENEK_TABLE = "#secenek_table";
var SECENEK_BODY = "#secenek_body";
var SECENEK_GOREVLER = "#secenekGorevler";
var SECENEK_HATALAR = "#secenekHatalar";

var DROPDOWNMENU_PROJE = "#ddProje";
var DROPDOWNMENU_GOREV = "#ddGorev";
var DROPDOWNMENU_HATA = "#ddHata";
var DROPDOWNMENU_PROJE_SECENEKLER = "#ddProjeSecenekleri";

var PROJEEKLE_DIV = "#projeekle_div";
var PROJEEKLE_TABLE = "#projeekle_table";
var PROJEEKLE_BODY = "#projeekle_body";
var PROJEEKLE_INPUTTEXT_PROJE_ADI = "projeekle_it_proje_adi";
var PROJEEKLE_INPUTTEXTAREA_ACIKLAMA = "projeekle_ita_aciklama";

var HATAEKLE_DIV = "#hataekle_div";
var HATAEKLE_TABLE = "#hataekle_table";
var HATAEKLE_BODY = "#hataekle_body";
var HATAEKLE_INPUTTEXT_HATA_ADI = "hataekle_it_proje_adi";
var HATAEKLE_INPUTTEXTAREA_ACIKLAMA = "hataekle_ita_aciklama";

var GOREVEKLE_DIV = "#gorevekle_div";
var GOREVEKLE_TABLE = "#gorevekle_table";
var GOREVEKLE_BODY = "#gorevekle_body";
var GOREVEKLE_INPUTTEXT_HATA_ADI = "gorevekle_it_proje_adi";
var GOREVEKLE_INPUTTEXTAREA_ACIKLAMA = "gorevekle_ita_aciklama";

var LISTELE_DIV = "#listele_div";
var LISTELE_TABLE = "#listele_table";
var LISTELE_BODY = "#listele_body";

var AYRINTI_DIV1_AYRINTI = "#ayrinti_div_ayrinti";
var AYRINTI_DIV_DURUM = "#ayrinti_div_durum";
var AYRINTI_TABLE = "#ayrinti_table";
var AYRINTI_TABLE_DURUM = "#ayrinti_table_durum";//jquery fonksiyonları için
var AYRINTI_TABLE_DURUM_2 = "ayrinti_table_durum";//saf js fonksiyonları için
var AYRINTI_DEGISIKLIK = "#degisiklik_table";
var AYRINTI_AYRAC = "div[id^='ayrinti_ayrac']";
var AYRINTI_BODY = "#ayrinti_body";
var AYRINTI_LINK_ETKIN = "ayrintiLinkEtkin";
var AYRINTI_LINK_ETKISIZ = "ayrintiLinkEtkisiz";

$(window).scroll(function ()//kaydırma çubuğu kaydırıldı
{
    if ($(this).scrollTop() > 10)
    {
        $('#baslik').addClass(CSS_UST_MENU_2);
    }
    else
    {
        $('#baslik').removeClass(CSS_UST_MENU_2);
    }
});

var prelaoderMarginHesapla = function ()
{
    var giris_div_spinner = $(GIRIS_DIV_SPINNER);
    var giris_overlay_back = $(GIRIS_OVERLAY_BACK);
    var pencere = $(window);

    var tarayiciEni = pencere.width();
    var elementEni = giris_div_spinner.width();
    var marginSol = (tarayiciEni - elementEni) / 2;

    var tarayiciBoyu = pencere.height();
    var elementBoyu = giris_div_spinner.height();
    var marginUst = (tarayiciBoyu - elementBoyu) / 2;

    giris_div_spinner.css({"margin-top": marginUst + "px", "margin-left": marginSol + "px"});
    giris_overlay_back.height(tarayiciBoyu);
};

//tarayıcı boyutu değiştikçe elementlarin ekranın ortasında kalabilmesi için marginleri yeniden hesaplıyor
var girisMarginHesapla = function ()
{
    var pencere = $(window);
    var giris_table = $(GIRIS_TABLE);
    var giris_div = $(GIRIS_DIV);

    var tarayiciEni = pencere.width();
    var elementEni = giris_table.width();
    var marginSol = (tarayiciEni - elementEni) / 2;

    var tarayiciBoyu = pencere.height();
    var elementBoyu = giris_table.height();
    var marginUst = (tarayiciBoyu - elementBoyu) / 2;

    giris_div.css({"margin-top": marginUst + "px", "margin-left": marginSol + "px"});
};

var secenekMarginHesapla = function ()//secenek ekranında sadece proje dropdown menüsü var
{
    var secenek_table = $(SECENEK_TABLE);
    var dropdown_menu_proje = $(DROPDOWNMENU_PROJE);
    var secenek_div = $(SECENEK_DIV);
    var pencere = $(window);

    var tarayiciEni = pencere.width();
    var elementEni = dropdown_menu_proje.width();
    var marginSol = (tarayiciEni - elementEni) / 2;

    var tarayiciBoyu = pencere.height();
    var elementBoyu = secenek_table.height();
    var marginUst = (tarayiciBoyu - elementBoyu) / 2;

    secenek_div.css({"margin-top": marginUst - 30 + "px", "margin-left": marginSol + "px"});
};

var secenekMarginHesapla2 = function ()//secenek ekranında bütün dropdown menüler var
{
    var secenek_table = $(SECENEK_TABLE);
    var dropdown_menu_proje_secenekler = $(DROPDOWNMENU_PROJE_SECENEKLER);
    var dropdown_menu_hata = $(DROPDOWNMENU_HATA);
    var dropdown_menu_gorev = $(DROPDOWNMENU_GOREV);
    var secenek_div = $(SECENEK_DIV);
    var pencere = $(window);

    var tarayiciEni = pencere.width();
    var elementEni = secenek_table.width();
    var marginSol = (tarayiciEni - elementEni) / 2;

    var tarayiciBoyu = pencere.height();
    var elementBoyu = secenek_table.height();
    var marginUst = (tarayiciBoyu - elementBoyu) / 2;

    secenek_div.css({"transition": "all 0.5s ease", "margin-left": marginSol + "px", "margin-top": marginUst - 30 + "px"});
    dropdown_menu_gorev.removeClass("saydam");
    dropdown_menu_hata.removeClass("saydam");
    dropdown_menu_proje_secenekler.removeClass("saydam");

    MENULER_VAR = 1;
    //DROPDOWNMENU_SECENEK_OBJECT.baslik.text("Seçenekler");
};

var secenekMarginHesapla3 = function ()//secenek ekranında sadece proje menüsü var
{
    var secenek_table = $(SECENEK_TABLE);
    var dropdown_menu_proje_secenekler = $(DROPDOWNMENU_PROJE_SECENEKLER);
    var dropdown_menu_hata = $(DROPDOWNMENU_HATA);
    var dropdown_menu_gorev = $(DROPDOWNMENU_GOREV);
    var secenek_div = $(SECENEK_DIV);
    var pencere = $(window);

    secenek_div.css("transition", "all 0.5s ease");

    if (MENULER_VAR === 0)//ekranda sadece proje dropdown menüsü var
    {
        dropdown_menu_proje_secenekler.removeClass("saydam");
    }
    else if (MENULER_VAR === 1)//ekranda secenek ve hata dropdown menüleri var. bunlar kaybolacak
    {
        dropdown_menu_gorev.addClass("gizli").removeClass("saydam");//proje div inin sola geçmesi ama diğer divlerin gözükmemesi için
        dropdown_menu_hata.addClass("gizli").removeClass("saydam");

        var tarayiciEni = pencere.width();
        var elementEni = secenek_table.width();
        var marginSol = (tarayiciEni - elementEni) / 2;

        var tarayiciBoyu = pencere.height();
        var elementBoyu = secenek_table.height();
        var marginUst = (tarayiciBoyu - elementBoyu) / 2;

        secenek_div.css({"margin-top": marginUst - 30 + "px", "margin-left": marginSol + "px"});
        MENULER_VAR = 0;
    }
    //DROPDOWNMENU_SECENEK_OBJECT.baslik.text("Seçenekler");
};

var projeEkleMarginHesapla = function ()
{
    var projeekle_table = $(PROJEEKLE_TABLE);
    var projeekle_div = $(PROJEEKLE_DIV);
    var pencere = $(window);

    var tarayiciEni = pencere.width();
    projeekle_table.width(tarayiciEni / 1.5);

    var elementEni = projeekle_table.width();
    var marginSol = (tarayiciEni - elementEni) / 2;

    var tarayiciBoyu = pencere.height();
    projeekle_div.css({"margin-top": USTMENU_YUKSEKLIK, "margin-left": marginSol + "px"});

    $("textarea[name$=" + PROJEEKLE_INPUTTEXTAREA_ACIKLAMA + "]").attr("rows", (tarayiciBoyu / 40));
};

var hataEkleMarginHesapla = function ()
{
    var hataekle_table = $(HATAEKLE_TABLE);
    var hataekle_div = $(HATAEKLE_DIV);
    var pencere = $(window);

    var tarayiciEni = pencere.width();
    hataekle_table.width(tarayiciEni / 1.5);

    var elementEni = hataekle_table.width();
    var marginSol = (tarayiciEni - elementEni) / 2;

    var tarayiciBoyu = pencere.height();

    hataekle_div.css({"margin-top": USTMENU_YUKSEKLIK, "margin-left": marginSol + "px"});
    $("textarea[name$=" + HATAEKLE_INPUTTEXTAREA_ACIKLAMA + "]").attr("rows", (tarayiciBoyu / 40));
};

var gorevEkleMarginHesapla = function ()
{
    var gorevekle_table = $(GOREVEKLE_TABLE);
    var gorevekle_div = $(GOREVEKLE_DIV);
    var pencere = $(window);

    var tarayiciEni = pencere.width();
    gorevekle_table.width(tarayiciEni / 1.5);

    var elementEni = gorevekle_table.width();
    var marginSol = (tarayiciEni - elementEni) / 2;

    var tarayiciBoyu = pencere.height();

    gorevekle_div.css({"margin-top": USTMENU_YUKSEKLIK, "margin-left": marginSol + "px"});
    $("textarea[name$=" + GOREVEKLE_INPUTTEXTAREA_ACIKLAMA + "]").attr("rows", (tarayiciBoyu / 40));
};

var listeleMarginHesapla = function ()
{
    var listele_table = $(LISTELE_TABLE);
    var listele_div = $(LISTELE_DIV);
    var pencere = $(window);

    var tarayiciEni = pencere.width();
    listele_table.width(tarayiciEni / 1.2);

    var elementEni = listele_table.width();
    var marginSol = (tarayiciEni - elementEni) / 2;

    listele_div.css({"margin-left": marginSol + "px", "margin-right": marginSol + "px", "margin-top": USTMENU_YUKSEKLIK});
};

var ayrintiMarginHesapla = function ()
{
    var ayriniti_table_durum = $(AYRINTI_TABLE_DURUM);
    var ayrinti_table = $(AYRINTI_TABLE);
    var ayrinti_degisiklik = $(AYRINTI_DEGISIKLIK);
    var ayrinti_ayrac = $(AYRINTI_AYRAC);
    var ayrinti_div_ayrinti = $(AYRINTI_DIV1_AYRINTI);
    var ayrinti_div_durum = $(AYRINTI_DIV_DURUM);
    var pencere = $(window);

    var tarayiciEni = pencere.width();
    var table2Eni = ayriniti_table_durum.width();

    ayrinti_table.width((tarayiciEni - table2Eni) / 1.4);
    ayrinti_degisiklik.width((tarayiciEni - table2Eni) / 1.4);
    ayrinti_ayrac.width((tarayiciEni - table2Eni) / 1.4);

    var elementEni = ayrinti_table.width();
    var marginSol = (tarayiciEni - elementEni - table2Eni) / 2.2;

    ayrinti_div_ayrinti.css({"margin-top": USTMENU_YUKSEKLIK, "margin-left": marginSol + "px"});
    ayrinti_div_durum.css({"margin-top": USTMENU_YUKSEKLIK, "margin-right": marginSol + "px"});
};

$(window).resize(function ()//tarayıcı boyutu değişti
{
    if ($(GIRIS_BODY).length > 0)//giris.xhtml sayfasında
    {
        girisMarginHesapla();
        prelaoderMarginHesapla();
    }
    else if ($(SECENEK_BODY).length > 0)//secenek.xhtml sayfasında
    {
        secenekMarginHesapla2();
    }
    else if ($(PROJEEKLE_BODY).length > 0)//projeekle.xhtml sayfasında
    {
        projeEkleMarginHesapla();
    }
    else if ($(HATAEKLE_BODY).length > 0)//hataekle.xhtml sayfasında
    {
        hataEkleMarginHesapla();
    }
    else if ($(GOREVEKLE_BODY).length > 0)//gorevekle.xhtml sayfasında
    {
        gorevEkleMarginHesapla();
    }
    else if ($(LISTELE_BODY).length > 0)//listele.xhtml sayfasında
    {
        listeleMarginHesapla();
    }
    else if ($(AYRINTI_BODY).length > 0)//ayrinti.xhtml sayfasında
    {
        ayrintiMarginHesapla();
    }
});

$(window).load(function ()//sayfa yüklendi
{
    if ($(GIRIS_BODY).length > 0)//giris.xhtml sayfasında
    {
        girisMarginHesapla();
        prelaoderMarginHesapla();

        //oturum açıksa kullanıcı adı ve şifre yazılı olur. o zaman buralar renkli gözüksün
        var element = $("input[name$=" + GIRIS_INPUTTEXT_KULLANICI_ADI + "]")
        var yazi = element.val();
        if (yazi !== YAZI_KULLANICI_ADI)
        {
            element.removeClass(CSS_ETKISIZ).addClass(CSS_ETKIN + " " + CSS_YESIL_GOLGE);
        }
        element = $("input[name$=" + GIRIS_INPUTTEXT_SIFRE + "]")
        yazi = element.val();
        if (yazi !== YAZI_SIFRE)
        {
            element.prop("type", "password");
            element.removeClass(CSS_ETKISIZ).addClass(CSS_ETKIN + " " + CSS_YESIL_GOLGE);
        }
        ////////////////////////////////////////77
    }
    else if ($(SECENEK_BODY).length > 0)//secenek.xhtml sayfasında
    {
        metal_init();
        secenekMarginHesapla();
    }
    else if ($(PROJEEKLE_BODY).length > 0)//projeekle.xhtml sayfasında
    {
        projeEkleMarginHesapla();
    }
    else if ($(HATAEKLE_BODY).length > 0)//hataekle.xhtml sayfasında
    {
        hataEkleMarginHesapla();
    }
    else if ($(GOREVEKLE_BODY).length > 0)//gorevekle.xhtml sayfasında
    {
        gorevEkleMarginHesapla();
    }
    else if ($(LISTELE_BODY).length > 0)//listele.xhtml sayfasında
    {
        $(LISTELE_TABLE).dynatable();
        listeleMarginHesapla();
    }
    else if ($(AYRINTI_BODY).length > 0)//ayrinti.xhtml sayfasında
    {
        ayrintiMarginHesapla();
    }
});

var onFocus = function (id)//focus bileşene geldi
{
    switch (id)
    {
        case 1:// giris.xhtml ==> kullanıcı adı

            var element = $("input[name$=" + GIRIS_INPUTTEXT_KULLANICI_ADI + "]");
            var yazi = element.val();
            if (yazi === YAZI_KULLANICI_ADI)
            {
                element.val("");
                element.removeClass(CSS_ETKISIZ).addClass(CSS_YESIL_GOLGE + " " + CSS_ETKIN);
            }
            break;

        case 2://giris.xhtml ==> sifre

            var element = $("input[name$=" + GIRIS_INPUTTEXT_SIFRE + "]")
            var yazi = element.val();
            if (yazi === YAZI_SIFRE)
            {
                element.val("");
                element.prop("type", "password");
                element.removeClass(CSS_ETKISIZ).addClass(CSS_ETKIN + " " + CSS_YESIL_GOLGE);
            }
            break;

        case 3://projeekle.xhtml ==> proje adı

            var element = $("input[name$=" + PROJEEKLE_INPUTTEXT_PROJE_ADI + "]")
            var yazi = element.val();
            if (yazi === YAZI_PROJE_ADI)
            {
                element.val("");
                element.removeClass(CSS_ETKISIZ).addClass(CSS_ETKIN + " " + CSS_YESIL_GOLGE);
            }
            break;

        case 4://projeekle.xhtml ==> acıklama

            var element = $("textarea[name$=" + PROJEEKLE_INPUTTEXTAREA_ACIKLAMA + "]")
            var yazi = element.val();
            if (yazi === YAZI_PROJE_ACIKLAMA)
            {
                element.val("");
                element.removeClass(CSS_ETKISIZ).addClass(CSS_ETKIN + " " + CSS_YESIL_GOLGE);
            }
            break;

        case 5://hataekle.xhtml ==> hata başlığı

            var element = $("input[name$=" + HATAEKLE_INPUTTEXT_HATA_ADI + "]")
            var yazi = element.val();
            if (yazi === YAZI_HATA_BASLIGI)
            {
                element.val("");
                element.removeClass(CSS_ETKISIZ).addClass(CSS_ETKIN + " " + CSS_YESIL_GOLGE);
            }
            break;

        case 6://hataekle.xhtml ==> acıklama

            var element = $("textarea[name$=" + HATAEKLE_INPUTTEXTAREA_ACIKLAMA + "]")
            var yazi = element.val();
            if (yazi === YAZI_HATA_ACIKLAMA)
            {
                element.val("");
                element.removeClass(CSS_ETKISIZ).addClass(CSS_ETKIN + " " + CSS_YESIL_GOLGE);
            }
            break;

        case 7://gorevekle.xhtml ==> hata başlığı

            var element = $("input[name$=" + GOREVEKLE_INPUTTEXT_HATA_ADI + "]")
            var yazi = element.val();
            if (yazi === YAZI_GOREV_BASLIGI)
            {
                element.val("");
                element.removeClass(CSS_ETKISIZ).addClass(CSS_ETKIN + " " + CSS_YESIL_GOLGE);
            }
            break;

        case 8://gorevekle.xhtml ==> acıklama

            var element = $("textarea[name$=" + GOREVEKLE_INPUTTEXTAREA_ACIKLAMA + "]")
            var yazi = element.val();
            if (yazi === YAZI_GOREV_ACIKLAMA)
            {
                element.val("");
                element.removeClass(CSS_ETKISIZ).addClass(CSS_ETKIN + " " + CSS_YESIL_GOLGE);
            }
            break;
    }
};

var onBlur = function (id)//focus bileşenden çıktı
{
    switch (id)
    {
        case 1:// giris.xhtml ==> kullanıcı adı

            var element = $("input[name$=" + GIRIS_INPUTTEXT_KULLANICI_ADI + "]");
            var yazi = element.val();
            if (yazi === "")
            {
                element.val(YAZI_KULLANICI_ADI);
                element.removeClass(CSS_ETKIN + " " + CSS_YESIL_GOLGE).addClass(CSS_ETKISIZ);
            }
            break;

        case 2://giris.xhtml ==> sifre

            var element = $("input[name$=" + GIRIS_INPUTTEXT_SIFRE + "]");
            var yazi = element.val();
            if (yazi === "")
            {
                element.prop("type", "text");
                element.val(YAZI_SIFRE);
                element.removeClass(CSS_ETKIN + " " + CSS_YESIL_GOLGE).addClass(CSS_ETKISIZ);
            }
            break;

        case 3://projeekle.xhtml ==> proje adı

            var element = $("input[name$=" + PROJEEKLE_INPUTTEXT_PROJE_ADI + "]");
            var yazi = element.val();
            if (yazi === "")
            {
                element.val(YAZI_PROJE_ADI);
                element.removeClass(CSS_ETKIN + " " + CSS_YESIL_GOLGE).addClass(CSS_ETKISIZ);
            }
            break;

        case 4 ://projeekle.xhtml ==> proje adı

            var element = $("textarea[name$=" + PROJEEKLE_INPUTTEXTAREA_ACIKLAMA + "]");
            var yazi = element.val();
            if (yazi === "")
            {
                element.val(YAZI_PROJE_ACIKLAMA);
                element.removeClass(CSS_ETKIN + " " + CSS_YESIL_GOLGE).addClass(CSS_ETKISIZ);
            }
            break;

        case 5://hataekle.xhtml ==> proje adı

            var element = $("input[name$=" + HATAEKLE_INPUTTEXT_PROJE_ADI + "]");
            var yazi = element.val();
            if (yazi === "")
            {
                element.val(YAZI_PROJE_ADI);
                element.removeClass(CSS_ETKIN + " " + CSS_YESIL_GOLGE).addClass(CSS_ETKISIZ);
            }
            break;

        case 6://hataekle.xhtml ==> proje adı

            var element = $("textarea[name$=" + HATAEKLE_INPUTTEXTAREA_ACIKLAMA + "]");
            var yazi = element.val();
            if (yazi === "")
            {
                element.val(YAZI_PROJE_ACIKLAMA);
                element.removeClass(CSS_ETKIN + " " + CSS_YESIL_GOLGE).addClass(CSS_ETKISIZ);
            }
            break;

        case 7://gorevekle.xhtml ==> proje adı

            var element = $("input[name$=" + GOREVEKLE_INPUTTEXT_PROJE_ADI + "]");
            var yazi = element.val();
            if (yazi === "")
            {
                element.val(YAZI_PROJE_ADI);
                element.removeClass(CSS_ETKIN + " " + CSS_YESIL_GOLGE).addClass(CSS_ETKISIZ);
            }
            break;

        case 8://gorevekle.xhtml ==> proje adı

            var element = $("textarea[name$=" + GOREVEKLE_INPUTTEXTAREA_ACIKLAMA + "]");
            var yazi = element.val();
            if (yazi === "")
            {
                element.val(YAZI_PROJE_ACIKLAMA);
                element.removeClass(CSS_ETKIN + " " + CSS_YESIL_GOLGE).addClass(CSS_ETKISIZ);
            }
            break;
    }
};

/*
 var onKeyDown = function()//şifre bileşenine yazılan harfleri * a çeviriyor
 {  
 var yazıBoyutu = $("input[name$=" + GIRIS_INPUTTEXT_SIFRE + "]").val().length;
 var sonHarf = $("input[name$=" + GIRIS_INPUTTEXT_SIFRE + "]").val().substr(yazıBoyutu - 1, 1);
 if (yazıBoyutu !== 0)
 {
 $("input[name$=" + GIRIS_INPUTTEXT_SIFRE + "]").val($("input[name$=" + GIRIS_INPUTTEXT_SIFRE + "]").val().replace(sonHarf, "*"));
 }
 };
 */

function DropDown(el, id)//secenek sayfasındaki dropdown menüleri açıyor
{
    this.id = id;
    this.dd = el;//dropdown menünün kendisi
    this.secenek = this.dd.find('ul.metal-items > li');//dropdown menü içindeki seçenekler
    this.index = -1;//menüde seçim yapılan elemanın indeksi
    this.initEvents();
}

DropDown.prototype = {
    initEvents: function ()
    {
        var objMenu = this;//dropdown menü nesnesi

        /*
        objMenu.dd.on('click', function (event)//dropdown menüye tıklandı
        {
        });
        */

        objMenu.secenek.on('click', function ()//menu seçeneğine tıklandı
        {
            var objSecenek = $(this);
            objMenu.index = objSecenek.index();

            if (objMenu.id === DROPDOWNMENU_PROJE_ID)
            {
                if (objSecenek.index() === DROPDOWNMENU_PROJE_INDEX_BUTUN_PROJELER)//menünün ikinci satırında bütün projeler var.  buraya tıklanırsa menünün sola kaymasına gerek yok sadece seçenekler butonu gelecek
                {
                    $(SECENEK_HATALAR).removeClass("gizli");//bütün projeler seçilirse secenek menüsünde hata ve görev çıksın
                    $(SECENEK_GOREVLER).removeClass("gizli");
                    secenekMarginHesapla3();
                }
                else if (objSecenek.index() !== DROPDOWNMENU_PROJE_INDEX_PROJE_EKLE)//menünün ilk satırında proje ekle var.  buraya tıklanırsa menünün sola kaymasına gerek yok
                {
                    $(DROPDOWNMENU_GOREV).removeClass("gizli").addClass("saydam");//proje div inin sola geçmesi ama diğer divlerin gözükmemesi için
                    $(DROPDOWNMENU_HATA).removeClass("gizli").addClass("saydam");
                    $(SECENEK_HATALAR).addClass("gizli");//belli bir proje seçilirse secenek menüsünde hata ve görev çıkmasın
                    $(SECENEK_GOREVLER).addClass("gizli");
                    secenekMarginHesapla2();
                }
            }
        });
    }
};

$(function ()
{
    new DropDown($(DROPDOWNMENU_GOREV), DROPDOWNMENU_GOREV_ID);
    new DropDown($(DROPDOWNMENU_HATA), DROPDOWNMENU_HATA_ID);
    new DropDown($(DROPDOWNMENU_PROJE), DROPDOWNMENU_PROJE_ID);
    DROPDOWNMENU_SECENEK_OBJECT = new DropDown($(DROPDOWNMENU_PROJE_SECENEKLER), DROPDOWNMENU_PROJE_SECENEKLER_ID);
<<<<<<< HEAD
=======
    $(document).click(function ()
    {
        $('.wrapper-dropdown-3').removeClass('active');
    });
>>>>>>> 4c5ece0c10404a10356439d8aa8d1a07dec1c56f
});

function seciliDurumuDegistir(secilenSatir)
{
    var satirSayisi = document.getElementById(AYRINTI_TABLE_DURUM_2).rows.length;
    for (var i = 0; i < satirSayisi - 3; i++)//tablonun son üç satırı durumlarla alakalı değil
    {
        var element = document.getElementById("formAyrinti:ayrintiLink" + i);
        if (element.className === AYRINTI_LINK_ETKIN)
        {
            element.className = AYRINTI_LINK_ETKISIZ;
            break;
        }
    }
    document.getElementById("formAyrinti:ayrintiLink" + secilenSatir).className = AYRINTI_LINK_ETKIN;
    document.getElementById("formAyrinti:seciliDurum").value = secilenSatir;
}

function silmeOnayiGosterProje()//proje sil seceneğinde çıkacak sor
{
    var yazi = "";

    if (DROPDOWNMENU_PROJE_INDEX_SECILI_PROJE === DROPDOWNMENU_PROJE_INDEX_BUTUN_PROJELER)//bütün projeler seçili
    {
        yazi = "Bütün projeler ve bütün kayıtlar silinsin mi?";
    }
    else
    {
        yazi = SECILI_PROJE_ADI.trim() + " projesi ve proje kayıtları silinsin mi?";
    }

    if (!confirm(yazi))
    {
        return false;
    }
    else
    {
        return true;
    }
}

function silmeOnayiGosterAyrinti(kayitIsmi)//ayrinti.xhtml sil e tıklayınca çıkacak soru
{
    var yazi = kayitIsmi + " silinsin mi?";

    if (!confirm(yazi))
    {
        return false;
    }
    else
    {
        return true;
    }
}

function monitor(data)//giris.xhtml'de animasyonun gözükmesini sağlıyor
{
    if (data.status === "begin")//düğmeye tıklandı
    {
        $(GIRIS_OVERLAY).fadeIn(500);
        $(GIRIS_OVERLAY_BACK).fadeIn(500);
    }
    else if (data.status === "success")//düğmenin tetiklediği işlem bitti
    {
        $(GIRIS_OVERLAY).fadeOut(500);
        $(GIRIS_OVERLAY_BACK).fadeOut(500);
    }
}
