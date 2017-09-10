package tr.hilal.trackmybus.db;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import tr.hilal.trackmybus.R;

/**
 * Created by TOSHIBA on 6/24/2015.
 */
public class BusDBopenHelper extends SQLiteOpenHelper {

    protected Context context;
    private SQLiteDatabase db = null;

    //Logtag
    private static final String LOGTAG = "HILSTAG";

    //Database Name
    private static final String DATABASE_NAME = "businfo.db";

    //Database Version
    private static final int DATABASE_VERSION = 345;

    //Table Names
    public static final String TABLE_STOP = "stop";
    public static final String TABLE_BUS = "bus";
    public static final String TABLE_SCHEDULE = "schedule";

    //Common Column Names
    public static final String KEY_ID = "id";

    //STOP Table Column Names
    public static final String STOP_NAME = "stopName";
    public static final String STOP_CODE = "stopCode";

    //BUS Table Column Names
    public static final String BUS_NAME = "routeName";
    public static final String CLASS_NAME = "className";

    //SCHEDULE Table Column Names
    public static final String BUS_ID = "busId";
    public static final String STOP_ID = "stopId";
    public static final String TIME = "time";



    // Table Create Statements

    // STOP table create statement
    private static final String CREATE_TABLE_STOP = "CREATE TABLE " + TABLE_STOP + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + STOP_NAME + " TEXT,"
            + STOP_CODE + " TEXT" +
            ")";


    //BUS Table Create Statement
    private static final String CREATE_TABLE_BUS = "CREATE TABLE " + TABLE_BUS + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            BUS_NAME + " TEXT," +
            CLASS_NAME + " TEXT" +
            ")";

    //SCHEDULE Table Create Statement
    private static final String CREATE_TABLE_SCHEDULE = "CREATE TABLE " + TABLE_SCHEDULE + "(" +
            KEY_ID + " INTEGER," +
            BUS_ID + " INTEGER," +
            STOP_ID + " INTEGER," +
            TIME + " TEXT" +
            ")";

    public BusDBopenHelper(Context context1) {
        super(context1, DATABASE_NAME, null, DATABASE_VERSION);

        context = context1;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STOP);
        Log.i(LOGTAG, "Stops table has been created");
        db.execSQL(CREATE_TABLE_BUS);
        Log.i(LOGTAG, "Bus table has been created");
        db.execSQL(CREATE_TABLE_SCHEDULE);
        Log.i(LOGTAG, "Schedule table has been created");

        //Parsing And Entering Data into Tables
        insertData(db, R.raw.stops);
        Log.d(LOGTAG, "ALL STOP NAMES INSERTED");
        insertData(db, R.raw.ksrtchotspots);



        insertData(db, R.raw.ksrtctvm);
        Log.d(LOGTAG, "1.TRIVANDRUM");
        Log.d(LOGTAG, "2.TRIVANDRUM CITY");
        insertData(db, R.raw.ksrtcattingal);
        Log.d(LOGTAG, "3.ATTINGAL");
        insertData(db, R.raw.ksrtcndd);
        Log.d(LOGTAG, "4.NEDUMANGADU");
        insertData(db, R.raw.ksrtcneyyatinkara);
        Log.d(LOGTAG, "5.NEYYATINKARA");
        Log.d(LOGTAG, "6.PAPPANAMCODU");
        insertData(db, R.raw.ksrtcvizhinjam);
        Log.d(LOGTAG, "7.VIZHINJAM");
        insertData(db, R.raw.ksrtckaniyapuram);
        Log.d(LOGTAG, "8.KANIYAPURAM");
        insertData(db, R.raw.ksrtckattakada);
        Log.d(LOGTAG, "9.KATAKADA");
        insertData(db, R.raw.ksrtckilimanoor);
        Log.d(LOGTAG, "10.KILIMANOOR");
        insertData(db, R.raw.ksrtcparasala);
        Log.d(LOGTAG, "11.PARASALA");
        Log.d(LOGTAG, "12.PEROORKADA");
        insertData(db, R.raw.ksrtcpoovar);
        Log.d(LOGTAG, "13.POOVAR");
        Log.d(LOGTAG, "14.VELLANADU");
        insertData(db, R.raw.ksrtcvellarada);
        Log.d(LOGTAG, "15.VELLARADA");
        insertData(db, R.raw.ksrtcvjmd);
        Log.d(LOGTAG, "16.VENJARAMOODU");
        Log.d(LOGTAG, "17.VIKASBHAVAN");
        insertData(db, R.raw.ksrtcvithura);
        Log.d(LOGTAG, "18.VITHURA");
        insertData(db, R.raw.ksrtcaryanadu);
        Log.d(LOGTAG, "19.ARYANADU");
        Log.d(LOGTAG, "20.PALODE");

        insertData(db, R.raw.ksrtckollam);
        Log.d(LOGTAG, "21.KOLLAM");
        insertData(db, R.raw.ksrtcktr);
        Log.d(LOGTAG, "22.KOTTARAKARA");
        Log.d(LOGTAG, "23.CHADAYAMANGALAM");
        Log.d(LOGTAG, "24.CHATHANNOOR");
        Log.d(LOGTAG, "25.KARUNAGAPALLY");
        insertData(db, R.raw.ksrtcpathanapuram);
        Log.d(LOGTAG, "26.PATHANAPURAM");
        insertData(db, R.raw.ksrtcpnlr);
        Log.d(LOGTAG, "27.PUNALUR");
        insertData(db, R.raw.ksrtcaryankavu);
        Log.d(LOGTAG, "28.ARYANKAVU");
        insertData(db, R.raw.ksrtckpza);
        Log.d(LOGTAG, "29.KULATHUPUZHA");

        insertData(db, R.raw.ksrtcpta);
        Log.d(LOGTAG, "30.PATHANAMTHITTA");
        insertData(db, R.raw.ksrtcthiruvalla);
        Log.d(LOGTAG, "31.THIRUVALLA");
        insertData(db, R.raw.ksrtcadoor);
        Log.d(LOGTAG, "32.ADOOR");
        insertData(db, R.raw.ksrtcmallapally);
        Log.d(LOGTAG, "34.MALLAPALLY");
        insertData(db, R.raw.ksrtcpdm);
        Log.d(LOGTAG, "35.PANDALAM");
        insertData(db, R.raw.ksrtcranni);
        Log.d(LOGTAG, "36.RANNY");
        insertData(db, R.raw.ksrtckonni);
        Log.d(LOGTAG, "37.KONNI");

        insertData(db, R.raw.ksrtcalappy);
        Log.d(LOGTAG, "38.ALAPPY");
        insertData(db, R.raw.ksrtccngr);
        Log.d(LOGTAG, "39.CHENGANUR");
        insertData(db, R.raw.ksrtccherthala);
        Log.d(LOGTAG, "40.CHERTHALA");
        insertData(db, R.raw.ksrtckayamkulam);
        Log.d(LOGTAG, "41.KAYAMKULAM");
        Log.d(LOGTAG, "42.HARIPPADU");
        insertData(db, R.raw.ksrtcmvka);
        Log.d(LOGTAG, "43.MAVELIKKARA");
        insertData(db, R.raw.ksrtcedathua);
        Log.d(LOGTAG, "44.EDATHUA");

        insertData(db, R.raw.ksrtckottayam);
        Log.d(LOGTAG, "45.KOTTAYAM");
        insertData(db, R.raw.ksrtccgry);
        Log.d(LOGTAG, "46.CHANGANACHERRY");
        insertData(db, R.raw.ksrtcpala);
        Log.d(LOGTAG, "47.PALA");
        insertData(db, R.raw.ksrtcetpa);
        Log.d(LOGTAG, "48.ERATTUPETTA");
        insertData(db, R.raw.ksrtcpnk);
        Log.d(LOGTAG, "49.PONKUNNAM");
        insertData(db, R.raw.ksrtcvaikom);
        Log.d(LOGTAG, "50.VAIKOM");
        insertData(db, R.raw.ksrtcemly);
        Log.d(LOGTAG, "51.ERUMELY");
        insertData(db, R.raw.ksrtcmdkm);
        Log.d(LOGTAG, "51A.MUNDAKAYAM");

        insertData(db, R.raw.ksrtcktpn);
        Log.d(LOGTAG, "52.KATTAPANA");
        insertData(db, R.raw.ksrtcndkm);
        Log.d(LOGTAG, "52A.NEDUMKANDAM");
        insertData(db, R.raw.ksrtckumily);
        Log.d(LOGTAG, "53.KUMILY");
        insertData(db, R.raw.ksrtctdpa);
        Log.d(LOGTAG, "54.THODUPUZHA");
        insertData(db, R.raw.ksrtcmlmtm);
        Log.d(LOGTAG, "55.MOOLAMATTAM");
        insertData(db, R.raw.ksrtcmoonnar);
        Log.d(LOGTAG, "56.MOONAR");

        insertData(db, R.raw.ksrtcekm);
        Log.d(LOGTAG, "57.ERNAKULAM");
        insertData(db, R.raw.ksrtcaluva);
        Log.d(LOGTAG, "58.ALUVA");
        insertData(db, R.raw.ksrtcmvpa);
        Log.d(LOGTAG, "59.MUVATTUPUZHA");
        Log.d(LOGTAG, "60.PERUMBAVOOR");
        insertData(db, R.raw.ksrtcank);
        Log.d(LOGTAG, "61.ANKAMALY");
        insertData(db, R.raw.ksrtckmgm);
        Log.d(LOGTAG, "62.KOTHAMANGALAM");
        Log.d(LOGTAG, "63.NORTH PARAVOOR");
        insertData(db, R.raw.ksrtcpiravom);
        Log.d(LOGTAG, "64.PIRAVOM");
        Log.d(LOGTAG, "65.KOOTHATTUKULAM");

        insertData(db, R.raw.ksrtctsr);
        Log.d(LOGTAG, "66.TRISSUR");
        insertData(db, R.raw.ksrtcchalakkudy);
        Log.d(LOGTAG, "67.CHALAKKUDY");
        insertData(db, R.raw.ksrtcgvr);
        Log.d(LOGTAG, "68.GURUVAYOOR");
        insertData(db, R.raw.ksrtckodungallur);
        Log.d(LOGTAG, "69.KODUNGALLOOR");
        insertData(db, R.raw.ksrtcmala);
        Log.d(LOGTAG, "70.MALA");
        insertData(db, R.raw.ksrtcijk);
        Log.d(LOGTAG, "71.IRINJALAKKUDA");
        Log.d(LOGTAG, "72.PUTHUKKADU");

        insertData(db, R.raw.ksrtcplk);
        Log.d(LOGTAG, "73.PALAKKAD");
        insertData(db, R.raw.ksrtcchittoor);
        Log.d(LOGTAG, "74.CHITTOOR");
        insertData(db, R.raw.ksrtcmannarkad);
        Log.d(LOGTAG, "75.MANNARKAD");
        insertData(db, R.raw.ksrtcvdcy);
        Log.d(LOGTAG, "76.VADAKKANCHERRY");

        insertData(db, R.raw.ksrtcmlpm);
        Log.d(LOGTAG, "77.MALAPRAM");
        insertData(db, R.raw.ksrtcnbr);
        Log.d(LOGTAG, "78.NILAMBOOR");
        insertData(db, R.raw.ksrtcpmna);
        Log.d(LOGTAG, "79.PERINTHALAMANNA");
        insertData(db, R.raw.ksrtcponnani);
        Log.d(LOGTAG, "80.PONNANI");

        insertData(db, R.raw.ksrtckozhikkodu);
        Log.d(LOGTAG, "81.KOZHIKKODU");
        insertData(db, R.raw.ksrtcthamarasery);
        Log.d(LOGTAG, "82.THAMARASERRY");
        insertData(db, R.raw.ksrtcthottilpalam);
        Log.d(LOGTAG, "83.THOTTILPALAM");
        Log.d(LOGTAG, "84.THIRUVAMPADY");
        Log.d(LOGTAG, "85.VADAKARA");

        insertData(db, R.raw.ksrtcsby);
        Log.d(LOGTAG, "86.SULTHAN BATHERY");
        insertData(db, R.raw.ksrtckalpata);
        Log.d(LOGTAG, "87.KALPATTA");
        insertData(db, R.raw.ksrtcmdy);
        Log.d(LOGTAG, "88.MANATHAVADY");

        insertData(db, R.raw.ksrtckannur);
        Log.d(LOGTAG, "89.KANNOOR");
        insertData(db, R.raw.ksrtcpayyanur);
        Log.d(LOGTAG, "90.PAYYANNUR");
        insertData(db, R.raw.ksrtcthalaserry);
        Log.d(LOGTAG, "91.THALASERRY");

        insertData(db, R.raw.ksrtckasargod);
        Log.d(LOGTAG, "92.KASARGOD");
        insertData(db, R.raw.ksrtckanjangadu);
        Log.d(LOGTAG, "93.KANJANGADU");




        insertData(db, R.raw.ksrtckkroad);
        Log.d(LOGTAG, "KKROAD");
        insertData(db, R.raw.chainksrtcktymekm);
        Log.d(LOGTAG, "KSRTC CHAIN KTYM EKM");
        insertData(db, R.raw.chainksrtcktymmlply);
        Log.d(LOGTAG, "KSRTC CHAIN KTYM MLPLY KZHY");
        insertData(db, R.raw.chainksrtckumilycumbum);
        Log.d(LOGTAG, "KSRTC CHAIN KMLY CUMBUM");
        insertData(db, R.raw.chainksrtcpnlrmdkm);
        Log.d(LOGTAG, "KSRTC CHAIN PNLR MDKM");
        insertData(db, R.raw.chainksrtcptacngrrly);
        Log.d(LOGTAG, "KSRTC CHAIN PTA CNGR");
        insertData(db, R.raw.chainksrtcptaklm);
        Log.d(LOGTAG, "KSRTC CHAINPTA KLM");
        insertData(db, R.raw.tvmmmcroad);
        Log.d(LOGTAG, "MC ROAD");
        insertData(db, R.raw.ksrtcbglr);
        Log.d(LOGTAG, "BGLR");
        insertData(db, R.raw.kurtckerala);
        Log.d(LOGTAG, "ACLF");
        insertData(db, R.raw.kurtcnonaclf);
        Log.d(LOGTAG, "NONAC LF");


        insertData(db, R.raw.pbkcgry);
        Log.d(LOGTAG, "PBK CGRY");
        insertData(db, R.raw.pbkemly);
        Log.d(LOGTAG, "PBK EMLY");
        insertData(db, R.raw.pbkvmh);
        Log.d(LOGTAG, "VYTTLA HB");
        insertData(db, R.raw.pbksuperclass);
        Log.d(LOGTAG, "SPRCLASS");
        insertData(db, R.raw.pbkkottayam);
        Log.d(LOGTAG, "KTYM");
        insertData(db, R.raw.pbkmdkm);
        Log.d(LOGTAG, "MDKM");
        insertData(db, R.raw.pbkkply);
        Log.d(LOGTAG, "KPLY");
        insertData(db, R.raw.pbkpta);
        Log.d(LOGTAG, "PBK PTA");
        insertData(db, R.raw.pbkidukki);
        Log.d(LOGTAG, "PBK IDUKKI");
        Log.d(LOGTAG, "SUCCESSSS");


        //insertData(db, R.raw.aclfkochi);
        //insertData(db, R.raw.aclftvm);




    }


    //Method To Insert DATA
    private void insertData(SQLiteDatabase db,int id) {
        Object obj;
        int i;
        obj = context.getResources().openRawResource(id);
        try {
            obj = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(((java.io.InputStream)
                    (obj)), null).getElementsByTagName("statement");
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        for(i=0;i<((NodeList) (obj)).getLength();i++) {
            if (i >= ((NodeList) (obj)).getLength())
            {
                return;
            }
            db.execSQL(((NodeList) (obj)).item(i).getChildNodes().item(0).getNodeValue());
            Log.d(LOGTAG, i + " Th Field Inserted");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULE);
        onCreate(db);
    }



}
