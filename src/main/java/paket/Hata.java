package paket;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class Hata implements Serializable
{

    private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://" + System.getenv("OPENSHIFT_MYSQL_DB_HOST") + ":" + System.getenv("OPENSHIFT_MYSQL_DB_PORT") + "/wu2?characterEncoding=utf8";
    private final String USER = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
    private final String PASS = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");

    private final String KULLANICI_ADI = "kullanıcı adı";
    private final String KULLANICI_SIFRESI = "şifre";
    private final String PROJE_ADI = "proje adı";
    private final String PROJE_ACIKLAMASI = "açıklama";
    private final String HATA_ACIKLAMASI = "açıklama";
    private final String GOREV_ACIKLAMASI = "açıklama";
    private final int TUR_HATA = 0;
    private final int TUR_GOREV = 1;
    private final int TUR_HEPSI = 2;
    private final String TARIH_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final int TUR_LISTELE = 0;//listele.xhtml sayfasından kayıt ayrıntısına ulaşmak için
    private final int TUR_LISTELE_ETKINLIK = 1;//listeleEtkinlik.xhtml sayfasından kayıt ayrıntısına ulaşmak için
    private final int BUTUN_PROJELER = -1;
    private final String HARF_GOREV = "G";
    private final String HARF_HATA = "H";
    private final int KAYIT_YENI = 0;
    private final int KAYIT_TAMAMLANAN = 1;
    private final int KAYIT_HEPSI = 2;

    private String kullaniciAdi;
    private String sifre;
    private int kullaniciID;
    private String projeAdi;
    private String projeAciklamasi;
    private int projeID;
    private List<Projeler> listeProje;//projelerin listesi
    private List<Kayitlar> listeKayitlar;
    private String hataBasligi;//hataekle.xhtml
    private String hataAciklamasi;//hataekle.xhtml
    private int hataNumarasi;//hata projenin kaçıncı hatası. vt deki id2 sutunu
    private String gorevBasligi;//gorevekle.xhtml
    private String gorevAciklamasi;//gorevekle.xhtml
    private int gorevNumarasi;//gorev projenin kaçıncı hatası. vt deki id2 sutunu
    private int listelenecekTur;//listele.xhtml sayfasında görevler mi hatalar mı listelenecek
    private int listelenecekDurum;//listele.xhtml sayfasında yeni, tamamlanan veay bütün kayıtlar mı listelenecek
    private String ayrintiAciklama;//ayrinti.xhtml sayfasında gösterilecek acıklama
    private String ayrintiBaslik;//ayrinti.xhtml sayfasında gösterilecek baslik
    private int ayrintiTur;//ayrinti.xhtml sayfasında gösterilecek baslik
    private List<String> listeAyrintiKayitDurumlari;//ayrinti.xhtml sayfasında hata için durumların listesi
    private int kayitSeciliDurum;//ayrinti.xhtml sayfasında kayıt için gecerli durum
    private List<Etkinlikler> listeDegisikliklarDurum;//ayrinti.xhtml sayfasında hata/gorev durum degisikliklerini tutuyor
    private List<Etkinlikler> listeDegisikliklerDurumHepsi;//listeleEtkinlik.xhtml sayfasında durum degisikliklerini tutuyor
    private List<String> listeTumDurumlars = new ArrayList<>();//bütün durumların listesi

    private String parametreKayitID;
    private String parametreProjeID;

    public Hata()
    {
        setKullaniciAdi(KULLANICI_ADI);
        setSifre(KULLANICI_SIFRESI);
        setProjeAdi(PROJE_ADI);
        setProjeAciklamasi(PROJE_ACIKLAMASI);

        listeleButunDurumlar();
    }

    private void listeleButunDurumlar()//bütün durumları listeye kaydeder
    {
        try
        {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement pst = conn.prepareStatement("select durum from tbl_durum_gorev");
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                String durum = rs.getString("durum");
                listeTumDurumlars.add(durum);
            }
        }
        catch (SQLException e)
        {
            System.out.println("hata 1 : " + e.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("hata 2 : " + e.getMessage());
        }
        setListeAyrintiKayitDurumlari(listeTumDurumlars);
    }

    /*araf.xhtml*/
    public void kayitYukle()
    {
        String ids;
        ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id1");
<<<<<<< HEAD
=======
        System.out.println("id1 : " + ids);
>>>>>>> 4c5ece0c10404a10356439d8aa8d1a07dec1c56f
        if (ids != null)
        {
            kayitAyrintisiniGetir(ids);
        }
    }

    public void kayitAyrintisiniGetir(String ids)//seçilen kaydin ayrintisini getirir
    {
        Kayitlar kyt = null;
        String query1 = "";
        String query2 = "";
        if (ids.contains(HARF_GOREV))
        {
            setAyrintiTur(TUR_GOREV);
            query1 = "select (select durum from tbl_durum_gorev where kod=tbl_etkinlik_gorev.durum) as drm, tarih from tbl_etkinlik_gorev where gorev_kod=?";
            query2 = "select a.aciklama, a.tarih, a.durum, b.durum from tbl_gorev as a, tbl_durum_gorev as b where a.durum = b.kod and a.kod = ?";
        }
        else if (ids.contains(HARF_HATA))
        {
            setAyrintiTur(TUR_HATA);
            query1 = "select (select durum from tbl_durum_hata where kod=tbl_etkinlik_hata.durum) as drm, tarih from tbl_etkinlik_hata where hata_kod=?";
            query2 = "select a.aciklama, a.tarih, a.durum, b.durum from tbl_hata as a, tbl_durum_hata as b where a.durum = b.kod and a.kod = ?";
        }

        if (!query1.isEmpty() && !query2.isEmpty())
        {
            List<Etkinlikler> listeEtknlk = new ArrayList<>();
            try
            {
                Class.forName(JDBC_DRIVER);
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

                PreparedStatement pst2 = conn.prepareStatement(query1);
                pst2.setString(1, ids);
                ResultSet rs2 = pst2.executeQuery();
                while (rs2.next())
                {
                    String tarih = rs2.getString("tarih");
                    Date date = new Date(Long.valueOf(tarih));
                    SimpleDateFormat sdf = new SimpleDateFormat(TARIH_FORMAT);
                    String formattedDate = sdf.format(date);

                    Etkinlikler etk = new Etkinlikler(rs2.getString("drm"), formattedDate);
                    listeEtknlk.add(etk);
                }

                PreparedStatement pst3 = conn.prepareStatement(query2);
                pst3.setString(1, ids);
                ResultSet rs3 = pst3.executeQuery();
                while (rs3.next())
                {
                    String aciklama = rs3.getString("a.aciklama");
                    String tarih = rs3.getString("a.tarih");
                    int durum = rs3.getInt("a.durum");
                    String durumYazi = rs3.getString("b.durum");

                    Date date = new Date(Long.valueOf(tarih));
                    SimpleDateFormat sdf = new SimpleDateFormat(TARIH_FORMAT);
                    String formattedDate = sdf.format(date);

                    kyt = new Kayitlar(aciklama, ids, formattedDate, durum, durumYazi);
                }
            }
            catch (SQLException e)
            {
                System.out.println("hata 3 : " + e.getMessage());
            }
            catch (ClassNotFoundException e)
            {
                System.out.println("hata 4 : " + e.getMessage());
            }

            setListeDegisikliklarDurum(listeEtknlk);//etkinlik listesi

            setAyrintiBaslik(ids);//üst menüdeki baslik
            setKayitSeciliDurum(kyt.getKayitDurumuID());
            setAyrintiAciklama(kyt.getKayitAciklamasi());

            try
            {
                String url = FacesContext.getCurrentInstance().getExternalContext().encodeActionURL("faces/ayrinti.xhtml?id=" + ids);
                FacesContext.getCurrentInstance().getExternalContext().redirect(url);

                //FacesContext.getCurrentInstance().getExternalContext().redirect("faces/ayrinti.xhtml?id=" + ids);
            }
            catch (IOException e)
            {
                System.out.println("RETURN 1 hata : " + e.getMessage());
            }
        }
    }
    /*araf.xhtml*/

    /*ayrinti.xhtml*/
    public void ayrintiKaydet()
    {
        String seciliDurum = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("formAyrinti:seciliDurum");

        String query1 = "";
        String query2 = "";
        if (getParametreKayitID().contains(HARF_GOREV))
        {
            setAyrintiTur(TUR_GOREV);
            query1 = "update tbl_gorev set durum=? where kod=?";
            query2 = "insert into tbl_etkinlik_gorev(gorev_kod, durum, tarih, proje_id) values(?, ?, ?, ?)";
        }
        else if (getParametreKayitID().contains(HARF_HATA))
        {
            setAyrintiTur(TUR_HATA);
            query1 = "update tbl_hata set durum=? where kod=?";
            query2 = "insert into tbl_etkinlik_hata(hata_kod, durum, tarih, proje_id) values(?, ?, ?, ?)";
        }
        if (!query1.isEmpty() && !query2.isEmpty())
        {
            try
            {
                Class.forName(JDBC_DRIVER);
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement pst = conn.prepareStatement(query1);
                pst.setInt(1, Integer.parseInt(seciliDurum));
                pst.setString(2, getParametreKayitID());
                int sonuc = pst.executeUpdate();

                PreparedStatement pst2 = conn.prepareStatement(query2);
                pst2.setString(1, getParametreKayitID());
                pst2.setInt(2, Integer.parseInt(seciliDurum));
                pst2.setLong(3, new Date().getTime());
                pst2.setString(4, getParametreProjeID());
                sonuc = pst2.executeUpdate();
            }
            catch (SQLException e)
            {
                System.out.println("hata 5 : " + e.getMessage());
            }
            catch (ClassNotFoundException e)
            {
                System.out.println("hata 6 : " + e.getMessage());
            }
        }
    }

    public void ayrintiSil()
    {
<<<<<<< HEAD
        //System.out.println("ayrintiSil : kayit id : " + getParametreKayitID() + " projeid : " + getParametreProjeID());
=======
        System.out.println("ayrintiSil : kayit id : " + getParametreKayitID() + " projeid : " + getParametreProjeID());
>>>>>>> 4c5ece0c10404a10356439d8aa8d1a07dec1c56f
        String query1 = "";
        String query2 = "";
        if (getParametreKayitID().contains(HARF_GOREV))
        {
            setAyrintiTur(TUR_GOREV);
            query1 = "delete from tbl_gorev where kod=?";
            query2 = "delete from tbl_etkinlik_gorev where gorev_kod=?";
        }
        else if (getParametreKayitID().contains(HARF_HATA))
        {
            setAyrintiTur(TUR_HATA);
            query1 = "delete from tbl_hata where kod=?";
            query2 = "delete from tbl_etkinlik_hata where hata_kod=?";
        }
        if (!query1.isEmpty() && !query2.isEmpty())
        {
            try
            {
                Class.forName(JDBC_DRIVER);
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement pst = conn.prepareStatement(query1);
                pst.setString(1, getParametreKayitID());
                int sonuc = pst.executeUpdate();

                PreparedStatement pst2 = conn.prepareStatement(query2);
                pst2.setString(1, getParametreKayitID());
                sonuc = pst2.executeUpdate();
            }
            catch (SQLException e)
            {
                System.out.println("hata 7 : " + e.getMessage());
            }
            catch (ClassNotFoundException e)
            {
                System.out.println("hata 8 : " + e.getMessage());
            }
        }
    }
    /*ayrinti.xhtml*/

    /*giris.xhtml*/
    public void kullaniciGirisiYap()
    {
        if (!getKullaniciAdi().equals(KULLANICI_ADI)
                && !getSifre().equals(KULLANICI_SIFRESI)
                && !getKullaniciAdi().isEmpty()
                && !getSifre().isEmpty())
        {
            try
            {
                Class.forName(JDBC_DRIVER);
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement pst = conn.prepareStatement("select id from tbl_kullanici where BINARY kullanici_adi=? and sifre=?");
                pst.setString(1, getKullaniciAdi());
                pst.setString(2, getSifre());
                ResultSet rs = pst.executeQuery();

                if (rs.next())
                {
                    int id = rs.getInt("id");
                    setKullaniciID(id);
                    setListeProje(getirProje());

                    try
                    {
                        String url = FacesContext.getCurrentInstance().getExternalContext().encodeActionURL("faces/secenek.xhtml");
                        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
                    }
                    catch (IOException e)
                    {
<<<<<<< HEAD
                        System.out.println("hata 40 : " + e.getMessage());
=======
>>>>>>> 4c5ece0c10404a10356439d8aa8d1a07dec1c56f
                    }
                    //return "secenek.xhtml?faces-redirect=true";
                }
                else
                {
                    setKullaniciAdi(KULLANICI_ADI);
                    setSifre(KULLANICI_SIFRESI);
<<<<<<< HEAD
=======

                    //return null;
>>>>>>> 4c5ece0c10404a10356439d8aa8d1a07dec1c56f
                }
            }
            catch (SQLException e)
            {
                System.out.println("hata 9 : " + e.getMessage());
<<<<<<< HEAD
=======
                //return null;
>>>>>>> 4c5ece0c10404a10356439d8aa8d1a07dec1c56f
            }
            catch (ClassNotFoundException e)
            {
                System.out.println("hata 10 : " + e.getMessage());
<<<<<<< HEAD
=======
                //return null;
>>>>>>> 4c5ece0c10404a10356439d8aa8d1a07dec1c56f
            }
        }
        else
        {
<<<<<<< HEAD
=======
            //return null;
>>>>>>> 4c5ece0c10404a10356439d8aa8d1a07dec1c56f
        }
    }

    public String kullaniciKaydiYap()
    {
        if (!getKullaniciAdi().isEmpty() && !getSifre().isEmpty() && !getKullaniciAdi().equals(KULLANICI_ADI) && !getSifre().equals(KULLANICI_SIFRESI))
        {
            try
            {
                Class.forName(JDBC_DRIVER);
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement pst = conn.prepareStatement("insert into tbl_Kullanici (kullanici_adi, sifre) values(?, ?)");
                pst.setString(1, getKullaniciAdi());
                pst.setString(2, getSifre());
                int sonuc = pst.executeUpdate();

                return "secenek.xhtml?faces-redirect=true";
            }
            catch (SQLException e)
            {
                System.out.println("hata 11 : " + e.getMessage());
                return null;
            }
            catch (ClassNotFoundException e)
            {
                System.out.println("hata 12 : " + e.getMessage());
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    public List<Projeler> getirProje()//projelerin isim ve id sinden listeTumDurumlar yapıyor
    {
        try
        {
            List<Projeler> liste = new ArrayList<>();

            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement pst = conn.prepareStatement("select isim, id from tbl_proje where sahib = ?");
            pst.setInt(1, getKullaniciID());
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                String isim = rs.getString("isim");
                int id = rs.getInt("id");
                liste.add(new Projeler(isim, id));
            }
            return liste;
        }
        catch (SQLException e)
        {
            System.out.println("hata 13 : " + e.getMessage());
            return null;
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("hata 14 : " + e.getMessage());
            return null;
        }
    }
    /*giris.xhtml*/

    /*secenek.xhtml*/
    public void butunProjeler()//secenek sayfasında bütün projeler seçildi
    {
        setProjeAdi("Bütün Projeler");//sile tıklanırsa confirmbox'taki yazıda çıkması için
        setProjeID(BUTUN_PROJELER);
    }

    public void secilenProjeyiGetir(int index)//seçilen projenin ismi ve id sini döndürür
    {
        setProjeID(getListeProje().get(index).projeID);
        setProjeAdi(getListeProje().get(index).projeAdi);
    }

    public String ekleProje()
    {
        setProjeAdi(PROJE_ADI);
        setProjeAciklamasi(PROJE_ACIKLAMASI);
        return "projeekle.xhtml?faces-redirect=true";
    }

    public String listeleKayitlar(int tur, int durum)
    {
        if (getProjeID() == BUTUN_PROJELER)
        {
            setListeKayitlar(getirKayitlarButunProjeler(tur));
        }
        else
        {
            setListeKayitlar(getirKayitlar(tur, durum));
        }
        return "listele.xhtml?" + "&pid=" + getProjeID() + "faces-redirect=true";
    }

    public List<Kayitlar> getirKayitlarButunProjeler(int tur)//bütün projeler için kayıtları döndürür
    {
        String query = "";
        if (tur == TUR_GOREV)
        {
            setListelenecekTur(TUR_GOREV);
            query = "select a.aciklama, a.kod, a.tarih, a.durum, b.durum from tbl_gorev as a, tbl_durum_gorev as b where a.durum = b.kod";
        }
        else if (tur == TUR_HATA)
        {
            setListelenecekTur(TUR_HATA);
            query = "select a.aciklama, a.kod, a.tarih, a.durum, b.durum from tbl_hata as a, tbl_durum_hata as b where a.durum = b.kod";
        }
        else if (tur == TUR_HEPSI)
        {
            setListelenecekTur(TUR_HEPSI);
            query = "select * from "
                    + "( "
                    + "select a.aciklama, a.kod, a.tarih, a.durum as durumID, b.durum as durumYazi, a.proje_id from tbl_hata as a, tbl_durum_hata as b where a.durum = b.kod "
                    + "union "
                    + "select c.aciklama, c.kod, c.tarih, c.durum as durumID, d.durum as durumYazi, c.proje_id from tbl_gorev as c, tbl_durum_gorev as d where c.durum = d.kod "
                    + ") "
                    + "as kayitlar";
        }

        if (!query.isEmpty())
        {
            try
            {
                List<Kayitlar> liste = new ArrayList<>();

                Class.forName(JDBC_DRIVER);
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement pst = conn.prepareStatement(query);
                ResultSet rs = pst.executeQuery();
                while (rs.next())
                {
                    String aciklama = rs.getString(1);
                    String kod = rs.getString(2);
                    String tarih = rs.getString(3);
                    int durum = rs.getInt(4);
                    String durumYazi = rs.getString(5);

                    Date date = new Date(Long.valueOf(tarih));
                    SimpleDateFormat sdf = new SimpleDateFormat(TARIH_FORMAT);
                    String formattedDate = sdf.format(date);

                    liste.add(new Kayitlar(aciklama, kod, formattedDate, durum, durumYazi));
                }
                return liste;
            }
            catch (SQLException e)
            {
                System.out.println("hata 15 : " + e.getMessage());
                return null;
            }
            catch (ClassNotFoundException e)
            {
                System.out.println("hata 16 : " + e.getMessage());
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    public List<Kayitlar> getirKayitlar(int tur, int drm)//tek bir proje için kayıtları döndürür
    {
        String query = "";
        switch (tur)
        {
            case TUR_GOREV:
                setListelenecekTur(TUR_GOREV);
                switch (drm)
                {
                    case KAYIT_HEPSI:
                        setListelenecekDurum(KAYIT_HEPSI);
                        query = "select a.aciklama, a.kod, a.tarih, a.durum, b.durum from tbl_gorev as a, tbl_durum_gorev as b where a.durum = b.kod and a.proje_id = ?";
                        break;

                    case KAYIT_TAMAMLANAN:
                        setListelenecekDurum(KAYIT_TAMAMLANAN);
                        query = "select a.aciklama, a.kod, a.tarih, a.durum, b.durum from tbl_gorev as a, tbl_durum_gorev as b where a.durum = b.kod and a.proje_id = ? and a.durum=4";
                        break;

                    case KAYIT_YENI:
                        setListelenecekDurum(KAYIT_YENI);
                        query = "select a.aciklama, a.kod, a.tarih, a.durum, b.durum from tbl_gorev as a, tbl_durum_gorev as b where a.durum = b.kod and a.proje_id = ? and a.durum=0";
                        break;
                }
                break;

            case TUR_HATA:
                setListelenecekTur(TUR_HATA);
                switch (drm)
                {
                    case KAYIT_HEPSI:
                        setListelenecekDurum(KAYIT_HEPSI);
                        query = "select a.aciklama, a.kod, a.tarih, a.durum, b.durum from tbl_hata as a, tbl_durum_hata as b where a.durum = b.kod and a.proje_id = ?";
                        break;

                    case KAYIT_TAMAMLANAN:
                        setListelenecekDurum(KAYIT_TAMAMLANAN);
                        query = "select a.aciklama, a.kod, a.tarih, a.durum, b.durum from tbl_hata as a, tbl_durum_hata as b where a.durum = b.kod and a.proje_id = ? and a.durum=4";
                        break;

                    case KAYIT_YENI:
                        setListelenecekDurum(KAYIT_YENI);
                        query = "select a.aciklama, a.kod, a.tarih, a.durum, b.durum from tbl_hata as a, tbl_durum_hata as b where a.durum = b.kod and a.proje_id = ? and a.durum=0";
                        break;
                }
                break;

            case TUR_HEPSI:
                setListelenecekTur(TUR_HEPSI);
                query = "select * from "
                        + "( "
                        + "select a.aciklama, a.kod, a.tarih, a.durum as durumID, b.durum as durumYazi, a.proje_id from tbl_hata as a, tbl_durum_hata as b where a.durum = b.kod "
                        + "union "
                        + "select c.aciklama, c.kod, c.tarih, c.durum as durumID, d.durum as durumYazi, c.proje_id from tbl_gorev as c, tbl_durum_gorev as d where c.durum = d.kod "
                        + ") "
                        + "as kayitlar "
                        + "where kayitlar.proje_id = ?";
                break;
        }
        if (!query.isEmpty())
        {
            try
            {
                List<Kayitlar> liste = new ArrayList<>();

                Class.forName(JDBC_DRIVER);
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, getProjeID());
                ResultSet rs = pst.executeQuery();
                while (rs.next())
                {
                    String aciklama = rs.getString(1);
                    String kod = rs.getString(2);
                    String tarih = rs.getString(3);
                    int durum = rs.getInt(4);
                    String durumYazi = rs.getString(5);

                    Date date = new Date(Long.valueOf(tarih));
                    SimpleDateFormat sdf = new SimpleDateFormat(TARIH_FORMAT);
                    String formattedDate = sdf.format(date);

                    liste.add(new Kayitlar(aciklama, kod, formattedDate, durum, durumYazi));
                }
                return liste;
            }
            catch (SQLException e)
            {
                System.out.println("hata 17 : " + e.getMessage());
                return null;
            }
            catch (ClassNotFoundException e)
            {
                System.out.println("hata 18 : " + e.getMessage());
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    public String silButunProjeler()//bütün projeleri siler
    {
        try
        {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement pst = conn.prepareStatement("delete from tbl_proje");
            int sonuc = pst.executeUpdate();

            return "secenek.xhtml?faces-redirect=true";
        }
        catch (SQLException e)
        {
            System.out.println("hata 19 : " + e.getMessage());
            return null;
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("hata 20 : " + e.getMessage());
            return null;
        }
    }

    public String silTekProje()//istenen projeyi siler
    {
        try
        {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement pst = conn.prepareStatement("delete from tbl_proje where id=?");
            pst.setInt(1, getProjeID());
            int sonuc = pst.executeUpdate();

            return "secenek.xhtml?faces-redirect=true";
        }
        catch (SQLException e)
        {
            System.out.println("hata 21 : " + e.getMessage());
            return null;
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("hata 22 : " + e.getMessage());
            return null;
        }
    }

    public String silProje()
    {
        if (getProjeID() == BUTUN_PROJELER)
        {
            return silButunProjeler();
        }
        else
        {
            return silTekProje();
        }
    }

    public String ekleGorev()
    {
        int toplam = 0;
        try
        {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement pst = conn.prepareStatement("SELECT MAX(id2) FROM tbl_gorev WHERE proje_id = ?");
            //PreparedStatement pst = conn.prepareStatement("SELECT COUNT(*) FROM tbl_gorev WHERE proje_id = ?");
            //PreparedStatement pst = conn.prepareStatement("SELECT MAX(id) FROM tbl_gorev");
            pst.setInt(1, getProjeID());
            ResultSet rs = pst.executeQuery();
            if (rs.next())
            {
                toplam = rs.getInt(1);
            }
        }
        catch (SQLException e)
        {
            System.out.println("hata 23 : " + e.getMessage());
            return null;
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("hata 24 : " + e.getMessage());
            return null;
        }

        setGorevNumarasi(toplam + 1);
        setGorevBasligi(getProjeID() + HARF_GOREV + String.valueOf(getGorevNumarasi()));
        setGorevAciklamasi(GOREV_ACIKLAMASI);

        return "gorevekle.xhtml?id=" + getGorevBasligi() + "&pid=" + getProjeID() + "faces-redirect=true";
    }

    public String ekleHata()
    {
        int toplam = 0;
        try
        {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement pst = conn.prepareStatement("SELECT MAX(id2) FROM tbl_hata WHERE proje_id = ?");

            pst.setInt(1, getProjeID());
            ResultSet rs = pst.executeQuery();
            if (rs.next())
            {
                toplam = rs.getInt(1);
            }
        }
        catch (SQLException e)
        {
            System.out.println("hata 25 : " + e.getMessage());
            return null;
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("hata 26 : " + e.getMessage());
            return null;
        }

        setHataNumarasi(toplam + 1);
        setHataBasligi(getProjeID() + HARF_HATA + String.valueOf(getHataNumarasi()));
        setHataAciklamasi(HATA_ACIKLAMASI);

        return "hataekle.xhtml?id=" + getHataBasligi() + "&pid=" + getProjeID() + "faces-redirect=true";
    }

    public String listeleEtkinlikler(int tur)
    {
        List<Etkinlikler> listeEtknlk = new ArrayList<>();
        String query1 = "";
        if (tur == TUR_GOREV)
        {
            setAyrintiTur(TUR_GOREV);
            query1 = "select (select durum from tbl_durum_gorev where kod=tbl_etkinlik_gorev.durum) as drm, tarih, gorev_kod as kod from tbl_etkinlik_gorev where proje_id=? order by gorev_kod";
        }
        else if (tur == TUR_HATA)
        {
            setAyrintiTur(TUR_HATA);
            query1 = "select (select durum from tbl_durum_hata where kod=tbl_etkinlik_hata.durum) as drm, tarih, hata_kod as kod from tbl_etkinlik_hata where proje_id=? order by hata_kod";
        }
        try
        {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement pst = conn.prepareStatement(query1);
            pst.setInt(1, getProjeID());
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                String tarih = rs.getString("tarih");

                Date date = new Date(Long.valueOf(tarih));
                SimpleDateFormat sdf = new SimpleDateFormat(TARIH_FORMAT);
                String formattedDate = sdf.format(date);

                Etkinlikler etk = new Etkinlikler(rs.getString("drm"), formattedDate, rs.getString("kod"));
                listeEtknlk.add(etk);
            }
        }
        catch (SQLException e)
        {
            System.out.println("hata 27 : " + e.getMessage());
            return null;
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("hata 28 : " + e.getMessage());
            return null;
        }

        setListeDegisikliklerDurumHepsi(listeEtknlk);

        //return "listeleEtkinlik.xhtml?t=" + TUR_GOREV + "&pid=" + getProjeID() + "faces-redirect=true";
        return "listeleEtkinlik.xhtml?&pid=" + getProjeID() + "faces-redirect=true";
    }

    public String listeleButunProjeEtkinlikleri()//butun projeler için etkinlikleri getirir
    {
        List<Etkinlikler> listeTumDD = new ArrayList<>();
        try
        {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            PreparedStatement pst = conn.prepareStatement("select (select durum from tbl_durum_hata where kod=tbl_etkinlik_hata.durum) as drm, tarih, hata_kod from tbl_etkinlik_hata");
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                String tarih = rs.getString("tarih");

                Date date = new Date(Long.valueOf(tarih));
                SimpleDateFormat sdf = new SimpleDateFormat(TARIH_FORMAT);
                String formattedDate = sdf.format(date);

                Etkinlikler dd = new Etkinlikler(rs.getString("drm"), formattedDate, rs.getString("hata_kod"), TUR_HATA);
                listeTumDD.add(dd);
            }

            PreparedStatement pst2 = conn.prepareStatement("select (select durum from tbl_durum_gorev where kod=tbl_etkinlik_gorev.durum) as drm, tarih, gorev_kod from tbl_etkinlik_gorev");
            ResultSet rs2 = pst2.executeQuery();
            while (rs2.next())
            {
                String tarih = rs2.getString("tarih");

                Date date = new Date(Long.valueOf(tarih));
                SimpleDateFormat sdf = new SimpleDateFormat(TARIH_FORMAT);
                String formattedDate = sdf.format(date);

                Etkinlikler dd = new Etkinlikler(rs2.getString("drm"), formattedDate, rs2.getString("gorev_kod"), TUR_GOREV);
                listeTumDD.add(dd);
            }
        }
        catch (SQLException e)
        {
            System.out.println("hata 29 : " + e.getMessage());
            return null;
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("hata 30 : " + e.getMessage());
            return null;
        }

        setListeDegisikliklerDurumHepsi(listeTumDD);
        setListelenecekTur(TUR_HEPSI);

        return "listeleEtkinlik.xhtml?faces-redirect=true";
    }

    public String listeleTekProjeEtkinlikleri()//secilen proje için bütün etkinlikleri getirir
    {
        List<Etkinlikler> listeTumDD = new ArrayList<>();
        try
        {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            /*PreparedStatement pst = conn.prepareStatement("select (select durum from tbl_durum_hata where kod=tbl_etkinlik_hata.durum) as drm, tarih, hata_kod from tbl_etkinlik_hata \n"
             + "union\n"
             + "select (select durum from tbl_durum_gorev where kod=tbl_etkinlik_gorev.durum) as drm, tarih, gorev_kod from tbl_etkinlik_gorev \n"
             + "order by hata_kod");*/

            PreparedStatement pst = conn.prepareStatement("select (select durum from tbl_durum_hata where kod=tbl_etkinlik_hata.durum) as drm, tarih, hata_kod from tbl_etkinlik_hata where proje_id=?");
            pst.setInt(1, getProjeID());
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                String tarih = rs.getString("tarih");

                Date date = new Date(Long.valueOf(tarih));
                SimpleDateFormat sdf = new SimpleDateFormat(TARIH_FORMAT);
                String formattedDate = sdf.format(date);

                Etkinlikler dd = new Etkinlikler(rs.getString("drm"), formattedDate, rs.getString("hata_kod"), TUR_HATA);
                listeTumDD.add(dd);
            }

            PreparedStatement pst2 = conn.prepareStatement("select (select durum from tbl_durum_gorev where kod=tbl_etkinlik_gorev.durum) as drm, tarih, gorev_kod from tbl_etkinlik_gorev where proje_id=?");
            pst2.setInt(1, getProjeID());
            ResultSet rs2 = pst2.executeQuery();
            while (rs2.next())
            {
                String tarih = rs2.getString("tarih");

                Date date = new Date(Long.valueOf(tarih));
                SimpleDateFormat sdf = new SimpleDateFormat(TARIH_FORMAT);
                String formattedDate = sdf.format(date);

                Etkinlikler dd = new Etkinlikler(rs2.getString("drm"), formattedDate, rs2.getString("gorev_kod"), TUR_GOREV);
                listeTumDD.add(dd);
            }
        }
        catch (SQLException e)
        {
            System.out.println("hata 31 : " + e.getMessage());
            return null;
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("hata 32 : " + e.getMessage());
            return null;
        }

        //setListeDegisikliklarDurum(listeTumDD);
        setListeDegisikliklerDurumHepsi(listeTumDD);
        setListelenecekTur(TUR_HEPSI);

        return "listeleEtkinlik.xhtml?faces-redirect=true";
    }

    public String listeleProjeEtkinlikleri()
    {
        if (getProjeID() == BUTUN_PROJELER)
        {
            return listeleButunProjeEtkinlikleri();
        }
        else
        {
            return listeleTekProjeEtkinlikleri();
        }
    }

    /*
     public String listeleProjeKayitlari()
     {
     return listeleKayitlar(TUR_HEPSI);
     }
     */

    /*secenek.xhtml*/

    /*projeekle.xhtml*/
    public String kaydetProje()
    {
        if (!getProjeAdi().isEmpty() && !getProjeAdi().equals(PROJE_ADI))
        {
            try
            {
                if (getProjeAciklamasi().equals(PROJE_ACIKLAMASI))
                {
                    setProjeAciklamasi("");
                }

                Class.forName(JDBC_DRIVER);
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement pst = conn.prepareStatement("insert into tbl_proje(isim, aciklama, sahib, tarih) values(?, ?, ?, ?)");
                pst.setString(1, getProjeAdi());
                pst.setString(2, getProjeAciklamasi());
                pst.setInt(3, getKullaniciID());
                pst.setLong(4, new Date().getTime());
                int sonuc = pst.executeUpdate();

                setListeProje(getirProje());

                return "secenek.xhtml?faces-redirect=true";
            }
            catch (SQLException e)
            {
                System.out.println("hata 33 : " + e.getMessage());
                return null;
            }
            catch (ClassNotFoundException e)
            {
                System.out.println("hata 34 : " + e.getMessage());
                return null;
            }
        }
        else
        {
            return null;
        }
    }
    /*projeekle.xhtml*/

    /*gorevekle.xhtml*/
    public String kaydetGorev()
    {
        if (!getGorevBasligi().isEmpty() && !getGorevAciklamasi().equals(GOREV_ACIKLAMASI))
        {
            try
            {
                if (getGorevAciklamasi().equals(GOREV_ACIKLAMASI))
                {
                    setGorevAciklamasi("");
                }

                Class.forName(JDBC_DRIVER);
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

                Long tarih = new Date().getTime();
                PreparedStatement pst = conn.prepareStatement("insert into tbl_gorev(aciklama, sahib, tarih, kod, proje_id, id2) values(?, ?, ?, ?, ?, ?)");
                pst.setString(1, getGorevAciklamasi());
                pst.setInt(2, getKullaniciID());
                pst.setLong(3, tarih);
                pst.setString(4, getParametreKayitID());
                pst.setString(5, getParametreProjeID());
                pst.setInt(6, getGorevNumarasi());
                int sonuc = pst.executeUpdate();

                PreparedStatement pst2 = conn.prepareStatement("insert into tbl_etkinlik_gorev(gorev_kod, durum, tarih, proje_id) values(?, ?, ?, ?)");
                pst2.setString(1, getParametreKayitID());
                pst2.setInt(2, 0);
                pst2.setLong(3, tarih);
                pst2.setString(4, getParametreProjeID());
                sonuc = pst2.executeUpdate();

                return "secenek.xhtml?faces-redirect=true";
            }
            catch (SQLException e)
            {
                System.out.println("hata 35 : " + e.getMessage());
                return null;
            }
            catch (ClassNotFoundException e)
            {
                System.out.println("hata 36 : " + e.getMessage());
                return null;
            }
        }
        else
        {
            return null;
        }
    }
    /*gorevekle.xhtml*/

    /*hataekle.xhtml*/
    public String kaydetHata()
    {
        if (!getHataBasligi().isEmpty() && !getHataAciklamasi().equals(HATA_ACIKLAMASI))
        {
            try
            {
                if (getHataAciklamasi().equals(HATA_ACIKLAMASI))
                {
                    setHataAciklamasi("");
                }

                Class.forName(JDBC_DRIVER);
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

                Long tarih = new Date().getTime();
                PreparedStatement pst = conn.prepareStatement("insert into tbl_hata(aciklama, sahib, tarih, kod, proje_id, id2) values(?, ?, ?, ?, ?, ?)");
                pst.setString(1, getHataAciklamasi());
                pst.setInt(2, getKullaniciID());
                pst.setLong(3, tarih);
                pst.setString(4, getParametreKayitID());
                pst.setString(5, getParametreProjeID());
                pst.setInt(6, getHataNumarasi());
                int sonuc = pst.executeUpdate();
<<<<<<< HEAD

                System.out.println("hata 37 : " + pst.toString());
=======
                
                System.out.println("hata : "+ pst.toString());
>>>>>>> 4c5ece0c10404a10356439d8aa8d1a07dec1c56f

                PreparedStatement pst2 = conn.prepareStatement("insert into tbl_etkinlik_hata(hata_kod, durum, tarih, proje_id) values(?, ?, ?, ?)");
                pst2.setString(1, getParametreKayitID());
                pst2.setInt(2, 0);
                pst2.setLong(3, tarih);
                pst2.setString(4, getParametreProjeID());
                sonuc = pst2.executeUpdate();

                return "secenek.xhtml?faces-redirect=true";
            }
            catch (SQLException e)
            {
<<<<<<< HEAD
                System.out.println("hata 38 : " + e.getMessage());
=======
                System.out.println("hata 37 : " + e.getMessage());
>>>>>>> 4c5ece0c10404a10356439d8aa8d1a07dec1c56f
                return null;
            }
            catch (ClassNotFoundException e)
            {
<<<<<<< HEAD
                System.out.println("hata 39 : " + e.getMessage());
=======
                System.out.println("hata 38 : " + e.getMessage());
>>>>>>> 4c5ece0c10404a10356439d8aa8d1a07dec1c56f
                return null;
            }
        }
        else
        {
            return null;
        }
    }
    /*hataekle.xhtml*/

    /*iç sınıflar*/
    public class Etkinlikler implements Serializable//ayrinti.xhtml sayfasında görev/hata durumlarındaki değişiklikleri tutan sınıf
    {

        String durum;
        String tarih;
        String kod;
        int tur;

        public Etkinlikler(String durum, String tarih, String kod)
        {
            this.durum = durum;
            this.tarih = tarih;
            this.kod = kod;
        }

        public Etkinlikler(String durum, String tarih, String kod, int tur)
        {
            this.durum = durum;
            this.tarih = tarih;
            this.kod = kod;
            this.tur = tur;
        }

        public Etkinlikler(String durum, String tarih)//hata ve görev ayrıntısında etkinlik göstermek için
        {
            this.durum = durum;
            this.tarih = tarih;
        }

        public int getTur()
        {
            return tur;
        }

        public void setTur(int tur)
        {
            this.tur = tur;
        }

        public String getDurum()
        {
            return durum;
        }

        public void setDurum(String durum)
        {
            this.durum = durum;
        }

        public String getTarih()
        {
            return tarih;
        }

        public void setTarih(String tarih)
        {
            this.tarih = tarih;
        }

        public String getKod()
        {
            return kod;
        }

        public void setKod(String kod)
        {
            this.kod = kod;
        }
    }

    public class Projeler implements Serializable//secenek.xhtml sayfasında proje dugmesinde listelenen projeleri tutan sınıf
    {

        String projeAdi;
        int projeID;

        public Projeler(String projeAdi, int projeID)
        {
            this.projeAdi = projeAdi;
            this.projeID = projeID;
        }

        public String getProjeAdi()
        {
            return projeAdi;
        }

        public void setProjeAdi(String projeAdi)
        {
            this.projeAdi = projeAdi;
        }

        public int getProjeID()
        {
            return projeID;
        }

        public void setProjeID(int projeID)
        {
            this.projeID = projeID;
        }
    };

    public class Kayitlar implements Serializable
    {

        private String kayitAciklamasi;
        private String kayitKodu;
        private String kayitTarihi;
        private int kayitDurumuID;
        private String kayitDurumuYazi;

        public Kayitlar(String kayitAciklamasi, String kayitKodu, String kayitTarihi, int kayitDurumuID, String kayitDurumuYazi)
        {
            this.kayitAciklamasi = kayitAciklamasi;
            this.kayitKodu = kayitKodu;
            this.kayitTarihi = kayitTarihi;
            this.kayitDurumuID = kayitDurumuID;
            this.kayitDurumuYazi = kayitDurumuYazi;
        }

        public String getKayitAciklamasi()
        {
            return kayitAciklamasi;
        }

        public void setKayitAciklamasi(String kayitAciklamasi)
        {
            this.kayitAciklamasi = kayitAciklamasi;
        }

        public String getKayitKodu()
        {
            return kayitKodu;
        }

        public void setKayitKodu(String kayitKodu)
        {
            this.kayitKodu = kayitKodu;
        }

        public String getKayitTarihi()
        {
            return kayitTarihi;
        }

        public void setKayitTarihi(String kayitTarihi)
        {
            this.kayitTarihi = kayitTarihi;
        }

        public int getKayitDurumuID()
        {
            return kayitDurumuID;
        }

        public void setKayitDurumuID(int kayitDurumuID)
        {
            this.kayitDurumuID = kayitDurumuID;
        }

        public String getKayitDurumuYazi()
        {
            return kayitDurumuYazi;
        }

        public void setKayitDurumuYazi(String kayitDurumuYazi)
        {
            this.kayitDurumuYazi = kayitDurumuYazi;
        }
    };
    /*iç sınıflar*/

    /*getter & setter*/
    public String getKullaniciAdi()
    {
        return kullaniciAdi;
    }

    public final void setKullaniciAdi(String kullaniciAdi)
    {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getSifre()
    {
        return sifre;
    }

    public final void setSifre(String sifre)
    {
        this.sifre = sifre;
    }

    public String getProjeAdi()
    {
        return projeAdi;
    }

    public final void setProjeAdi(String projeAdi)
    {
        this.projeAdi = projeAdi;
    }

    public String getProjeAciklamasi()
    {
        return projeAciklamasi;
    }

    public final void setProjeAciklamasi(String projeAciklamasi)
    {
        this.projeAciklamasi = projeAciklamasi;
    }

    public int getProjeID()
    {
        return projeID;
    }

    public void setProjeID(int projeID)
    {
        this.projeID = projeID;
    }

    public int getKullaniciID()
    {
        return kullaniciID;
    }

    public void setKullaniciID(int kullaniciID)
    {
        this.kullaniciID = kullaniciID;
    }

    public List<Projeler> getListeProje()
    {
        return listeProje;
    }

    public void setListeProje(List<Projeler> listeProje)
    {
        this.listeProje = listeProje;
    }

    public String getHataBasligi()
    {
        return hataBasligi;
    }

    public void setHataBasligi(String hataBasligi)
    {
        this.hataBasligi = hataBasligi;
    }

    public String getHataAciklamasi()
    {
        return hataAciklamasi;
    }

    public void setHataAciklamasi(String hataAciklamasi)
    {
        this.hataAciklamasi = hataAciklamasi;
    }

    public String getGorevBasligi()
    {
        return gorevBasligi;
    }

    public void setGorevBasligi(String gorevBasligi)
    {
        this.gorevBasligi = gorevBasligi;
    }

    public String getGorevAciklamasi()
    {
        return gorevAciklamasi;
    }

    public void setGorevAciklamasi(String gorevAciklamasi)
    {
        this.gorevAciklamasi = gorevAciklamasi;
    }

    public int getListelenecekTur()
    {
        return listelenecekTur;
    }

    public void setListelenecekTur(int listelenecekTur)
    {
        this.listelenecekTur = listelenecekTur;
    }

    public int getTUR_HATA()
    {
        return TUR_HATA;
    }

    public int getTUR_GOREV()
    {
        return TUR_GOREV;
    }

    public int getTUR_HEPSI()
    {
        return TUR_HEPSI;
    }

    public int getTUR_LISTELE()
    {
        return TUR_LISTELE;
    }

    public int getTUR_LISTELE_ETKINLIK()
    {
        return TUR_LISTELE_ETKINLIK;
    }

    public String getAyrintiAciklama()
    {
        return ayrintiAciklama;
    }

    public void setAyrintiAciklama(String ayrintiAciklama)
    {
        this.ayrintiAciklama = ayrintiAciklama;
    }

    public String getAyrintiBaslik()
    {
        return ayrintiBaslik;
    }

    public void setAyrintiBaslik(String ayrintiBaslik)
    {
        this.ayrintiBaslik = ayrintiBaslik;
    }

    public int getHataNumarasi()
    {
        return hataNumarasi;
    }

    public void setHataNumarasi(int hataNumarasi)
    {
        this.hataNumarasi = hataNumarasi;
    }

    public int getGorevNumarasi()
    {
        return gorevNumarasi;
    }

    public void setGorevNumarasi(int gorevNumarasi)
    {
        this.gorevNumarasi = gorevNumarasi;
    }

    public int getAyrintiTur()
    {
        return ayrintiTur;
    }

    public void setAyrintiTur(int ayrintiTur)
    {
        this.ayrintiTur = ayrintiTur;
    }

    public List<String> getListeAyrintiKayitDurumlari()
    {
        return listeAyrintiKayitDurumlari;
    }

    public void setListeAyrintiKayitDurumlari(List<String> listeAyrintiKayitDurumlari)
    {
        this.listeAyrintiKayitDurumlari = listeAyrintiKayitDurumlari;
    }

    public List<Etkinlikler> getListeDegisikliklarDurum()
    {
        return listeDegisikliklarDurum;
    }

    public void setListeDegisikliklarDurum(List<Etkinlikler> listeDegisikliklarDurum)
    {
        this.listeDegisikliklarDurum = listeDegisikliklarDurum;
    }

    public List<Etkinlikler> getListeDegisikliklerDurumHepsi()
    {
        return listeDegisikliklerDurumHepsi;
    }

    public void setListeDegisikliklerDurumHepsi(List<Etkinlikler> listeDegisikliklerDurumHepsi)
    {
        this.listeDegisikliklerDurumHepsi = listeDegisikliklerDurumHepsi;
    }

    public String getParametreKayitID()
    {
        return parametreKayitID;
    }

    public void setParametreKayitID(String parametreKayitID)
    {
        this.parametreKayitID = parametreKayitID;
    }

    public String getParametreProjeID()
    {
        return parametreProjeID;
    }

    public void setParametreProjeID(String parametreProjeID)
    {
        this.parametreProjeID = parametreProjeID;
    }

    public List<String> getListeTumDurumlars()
    {
        return listeTumDurumlars;
    }

    public void setListeTumDurumlars(List<String> listeTumDurumlars)
    {
        this.listeTumDurumlars = listeTumDurumlars;
    }

    public int getKayitSeciliDurum()
    {
        return kayitSeciliDurum;
    }

    public void setKayitSeciliDurum(int kayitSeciliDurum)
    {
        this.kayitSeciliDurum = kayitSeciliDurum;
    }

    public List<Kayitlar> getListeKayitlar()
    {
        return listeKayitlar;
    }

    public void setListeKayitlar(List<Kayitlar> listeKayitlar)
    {
        this.listeKayitlar = listeKayitlar;
    }

    public int getKAYIT_YENI()
    {
        return KAYIT_YENI;
    }

    public int getKAYIT_TAMAMLANAN()
    {
        return KAYIT_TAMAMLANAN;
    }

    public int getKAYIT_HEPSI()
    {
        return KAYIT_HEPSI;
    }

    public int getListelenecekDurum()
    {
        return listelenecekDurum;
    }

    public void setListelenecekDurum(int listelenecekDurum)
    {
        this.listelenecekDurum = listelenecekDurum;
    }

    public void deneme()
    {
        String ids = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        System.out.println("DENEME : id : " + ids);
        //return "null";
    }
}
