package XZPK;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;
import java.text.Collator;
import java.util.List;
import java.util.Locale;
import java.util.ArrayList;
import java.nio.charset.Charset;
import java.util.Random;
import java.lang.Math;


public class XZGraphcolor {
    //基本信息数据结构
    int Crs_Num = 0;
    int Class_Num = 0;
    int Tea_Num = 0;
    int KCL = 0;//一周多少节课
    int Weak_Day = 0;
    int Day_Crs = 0;
    int Day_Crs_Mr = 0;
    int Day_Crs_Af = 0;
    int PDay[] = null;
    int PCr[]= null;
    int MCls[]= null;
    int MCrs[]= null;
    int Class_Crs_CD[][]= null;
    int Min_Day[]= null;
    int Max_Day[]= null;
    int Max_Mr[] = null;
    int Max_Af[] = null;
    int LTK_Week[]= null;//课程每周的节数
    int Crs_Attri[]= null;
    int Tct[]= null;//是否集中排课
    int Vtx_len = 0;
    //课表数据结构
    int Class_Crs[][][]= null;//班级课表
    int Tea_Crs[][][]= null;//教师课表
    int Stu_Crs[][][]= null;//学生课表
    //int Key_Stu_Crs[][][]= null;//重点班学生课表
    //排课时间限制数据结构
    int Class_AvrT[][][]= null;
    int Tea_AvrT[][][]= null;
    int Crs_AvrT[][][]= null;
    int AVB_Cl[][]= null;
    //图着色数据结构
    int MNum = 0;
    int N_vtx = 0;
    int Edge[][]= null;
    int A_Matrix[][]= null;
    int Set_cl[][]= null;
    int Set_Crs[][][]= null;
    int Par[]= null;
    int Node_Tea[][]= null;
    int Node_Stu[][] = null;
    int Node_Course[][]= null;
    int Node_Class[][]= null;
    int Node_Avb[][]= null;
    int Tea_CP[][]= null;
    int Synthesis_Node[]= null;//合成点
    int Synthesis_Node_Num = 0;//合成数量
    int Tea_CP_Sign[][]= null;//教师教的课程的标记
    int Color[]= null;
    int Initial_Color[]= null;
    int Best_Color[]= null;
    int Final_Color[]= null;
    int Node_Tea_Sign[]= null;
    //检验课表优劣数组
    int Day_Set[] = null;
    int set[] = null;
    int Class_Score[]= null;//班级课表优劣
    int Tea_JP_Score[]= null;//教师课表优劣
    int Tea_Score[]= null;//二次教师课表优劣
    int TScr[]= null;
    int Tea_Scr[][]= null;
    int daycr[]= null;
    int daylt[]= null;
    int All_cl[]= null;//一周内课程的分布情况
    int Ctwo = 0;
    int C_Time = 0;
    int Cct[] = null;
    int Crs_srt_cl[][]= null;
    int JP_KC[][]= null;
    //tabu搜索数据结构
    int tabuh[]= null;
    int TTL = 0;
    int f = 0;
    int f_crs = 0;
    int f_tea = 0;
    int f_best = 0;
    int fc = 0;
    int fc_best = 0;
    int Delta_Matrix[][]= null;
    int TabuTenure[][]= null;
    int best_x[]= null;
    int best_v[]= null;
    int Delta_Crs[][]= null;
    int Delta_Tpj[][]= null;
    int Delta_Ttm[][]= null;
    int dgl[]= null;
    //合班课数据结构
    int HBK_Num = 0;
    int HBK[][]= null;
    int HBK_C[]= null;
    int Class_Crs_HEBAN[][]= null;
    //课程信息数据结构
    ArrayList< ArrayList<Integer> > Tname = null;//任课教师二维表
    ArrayList< ArrayList<Integer> > Cnumber = null;//课程数量二维表
    ArrayList<Integer>CourseID = null;
    ArrayList<String> Coursename = null;//课程名字
    ArrayList<Integer> Grade = null;//年级
    ArrayList<Integer> Class = null;//班级
    ArrayList<String> Tea_Name = null;
    ArrayList<Integer> Tea_ID = null;

    int Class_Crs_Num[][]= null;
    int Class_Crs_Num_L[][]= null;
    int Class_Tea[][]= null;
    //学生信息数据结构
    int Stu_Num = 0;
    //int Key_Stu_Num = 0;
    int Stu_ID[]= null;
    int Stu_Class[]= null;
    ArrayList<String> Stu_Course= null;

    //选考课数据结构
    int XK_Class_Set[] = null;
    int XK_Tea_Set[]= null;
    int XK_Stu_Set[]= null;
    //单双周数据结构
    int DSZ_Num = 0;
    int DSZ[][]= null;
    int DS = 0;
    //预排课数据结构
    int YPK_Num[]= null;
    int YPK[]= null;
    //课程检查数据结构
    int Class_Rst[][];//哪个班触发了哪条规则
    int Tea_Rst[][];//哪个教师触发了哪条规则
    int Class_Crs_Rst[][][];//哪节课触发了哪条规则
    int Tea_Crs_Rst[][][];//哪节课触发了哪条规则
    int Class_Weak[][][];//哪一天
    int Tea_Weak[][][];//哪一天
    Collator co = Collator.getInstance(Locale.CHINA);
    Random rand = new Random();


    public XZGraphcolor() throws IOException {

    }

    public void setData(BasicDataInfo inputBasicData){
        int i, j, k;
        int class_id = 0;
        int day = 0;
        int tea_id = 0;
        int crs_id = 0;
        int m,l;
        int x,y;
        Coursename = new ArrayList();
        CourseID = new ArrayList();
        Class = new ArrayList();
        Tea_Name = new ArrayList();


        Weak_Day = inputBasicData.getWeakDay();
        Day_Crs_Mr = inputBasicData.getDayCrsMr();
        Day_Crs_Af = inputBasicData.getDayCrsAf();
        Day_Crs = inputBasicData.getDayCrs();
        KCL = Weak_Day*Day_Crs;
        Class_Num = inputBasicData.getClassNum();
        List<Integer> classListInfo = inputBasicData.getClassID();
        for(i = 0;i<classListInfo.size();i++)
        {
            Class.add(classListInfo.get(i));
        }
        Crs_Num = inputBasicData.getCrsNum();
        List<CrsListInfo> crsListInfo = inputBasicData.getCrsListInfo();
        for(CrsListInfo crsInfo:crsListInfo)
        {
            CourseID.add(crsInfo.getCourseID());
            Coursename.add(crsInfo.getCourseName());
        }
        Tea_Num = inputBasicData.getTeaNum();
        List<String> teaListInfo = inputBasicData.getTeaName();
        for(i=0;i<teaListInfo.size();i++)
        {
            Tea_Name.add(teaListInfo.get(i));
        }

        //System.out.print("Crs_Num =" + Crs_Num + "\n");
        //System.out.print("Class_Num =" + Class_Num + "\n");
        //System.out.print("Tea_Num =" + Tea_Num + "\n");

        Class_Crs_Num = new int[Class_Num + 1][Crs_Num + 1];
        for (i = 0; i <= Class_Num; i++)
            for (j = 0; j <= Crs_Num; j++)
                Class_Crs_Num[i][j] = 0;

        Class_Crs_Num_L = new int[Class_Num + 1][Crs_Num + 1];
        for (i = 0; i <= Class_Num; i++)
            for (j = 0; j <= Crs_Num; j++)
                Class_Crs_Num_L[i][j] = 0;

        Class_Tea = new int[Class_Num + 1][Crs_Num + 1];
        for (i = 0; i <= Class_Num; i++)
            for (j = 0; j <= Crs_Num; j++)
                Class_Tea[i][j] = 0;
        Cct = new int[Crs_Num + 1];//优先排课
        for (i = 0; i <= Crs_Num; i++)
            Cct[i] = 0;
        Tct = new int[Tea_Num + 1];//教师集中排课
        for (i = 0; i <= Tea_Num; i++)
            Tct[i] = 1;
        Class_AvrT = new int [Class_Num + 1][Weak_Day + 1][Day_Crs + 1];
        Tea_AvrT = new int [Tea_Num + 1][Weak_Day + 1][Day_Crs + 1];
        Crs_AvrT = new int [Crs_Num + 1][Weak_Day + 1][Day_Crs + 1];
        ////////////////////////////////////////////////////////
        for (i = 0; i <= Class_Num; i++)
        {
            for (j = 0; j <= Weak_Day; j++)
            {
                for (k = 0; k <= Day_Crs; k++)
                    Class_AvrT[i][j][k] = 1;
            }
        }
        for (i = 0; i <= Tea_Num; i++)
        {
            for (j = 0; j <= Weak_Day; j++)
            {
                for (k = 0; k <= Day_Crs; k++)
                    Tea_AvrT[i][j][k] = 1;
            }
        }
        for (i = 0; i <= Crs_Num; i++)
        {
            for (j = 0; j <= Weak_Day; j++)
            {
                for (k = 0; k <= Day_Crs; k++)
                    Crs_AvrT[i][j][k] = 1;
            }
        }

        Ctwo = inputBasicData.getCtwo();
        if(Ctwo == 1)//触发优先排课
        {
            C_Time = inputBasicData.getCtwoTime();
            List<Integer> ctwoCrsList = inputBasicData.getCtwoCrs();
            for(i=0;i<ctwoCrsList.size();i++)
            {
                for(j=0;j<CourseID.size();j++)
                {
                    if(CourseID.get(j) == ctwoCrsList.get(i))
                    {
                        crs_id = j + 1;
                        break;
                    }
                }
                Cct[crs_id] = 1;
            }
        }

        List<ClassCrsInfo> classCrsInfo = inputBasicData.getClassCrsInfo();
        for(ClassCrsInfo classCrs:classCrsInfo)
        {
            for(j=0;j<Class.size();j++)
            {
                if(Class.get(j) == classCrs.getClassID() )
                {
                    class_id = j + 1;//哪个班级
                    break;
                }
            }
            List<CourseInfo> courseInfo = classCrs.getCourseInfoList();
            for(CourseInfo course:courseInfo)
            {
                for(j=0;j<CourseID.size();j++)
                {
                    if(CourseID.get(j) == course.getCourseID())
                    {
                        crs_id = j + 1;
                        break;
                    }
                }
                Class_Crs_Num[class_id][crs_id] = course.getCourseNum();
                Class_Crs_Num_L[class_id][crs_id] = course.getCourseLTNum();
                if(course.getCourseTeaNum() == 0)
                {
                    Class_Tea[class_id][crs_id] = -1;//自习课
                }
                else if(course.getCourseTeaNum() == 1)
                {
                    for(x=0;x<Tea_Name.size();x++)
                    {
                        if(co.compare(Tea_Name.get(x),course.getCourseTeaName()) == 0)
                        {
                            tea_id = x + 1;
                            break;
                        }
                    }
                    Class_Tea[class_id][crs_id] = tea_id;
                    //System.out.print(Class_Tea[class_id][j+1]);
                }
            }

        }

        //////////////////////////////////////////////////////////////
        Class_Crs_HEBAN = new int[Class_Num + 1][Crs_Num + 1];

        for (i = 1; i <= Class_Num; i++)
            for (j = 1; j <= Crs_Num; j++)
                Class_Crs_HEBAN[i][j] = i;

        Class_Crs_CD = new int[Class_Num + 1][Crs_Num + 1];
        Tea_CP = new int [Tea_Num + 1][Class_Num + 1];//教师教了哪几个班？
        Tea_CP_Sign = new int [Tea_Num + 1][Class_Num + 1];//教师教了哪几个班？
        Class_Crs = new int [Class_Num + 1][Weak_Day + 1][Day_Crs + 1];
        Tea_Crs = new int [Tea_Num + 1][Weak_Day + 1][Day_Crs + 1];
        for (i = 0; i <= Class_Num; i++)
        {
            for (j = 0; j <= Weak_Day; j++)
            {
                for (k = 0; k <= Day_Crs; k++)
                    Class_Crs[i][j][k] = 0;
            }
        }
        for (i = 0; i <= Tea_Num; i++)
        {
            for (j = 0; j <= Weak_Day; j++)
            {
                for (k = 0; k <= Day_Crs; k++)
                    Tea_Crs[i][j][k] = 0;
            }
        }
        Class_Crs_Rst = new int [Class_Num + 1][Weak_Day + 1][Day_Crs + 1];
        Tea_Crs_Rst = new int [Tea_Num + 1][Weak_Day + 1][Day_Crs + 1];
        Class_Weak = new int [Class_Num*Crs_Num + 1][Weak_Day + 1][9 + 1];
        Tea_Weak = new int [Tea_Num + 1][Weak_Day + 1][5 + 1];

        l = Class_Num*Crs_Num + 1;//节点数
        m = Weak_Day*Day_Crs + 1;//着色数
        Class_Rst = new int [l + 1][30 + 1];
        for (i = 0; i <= l; i++)
        {
            for (j = 0; j <= 30; j++)
            {
                Class_Rst[i][j] = 0;
            }
        }
        Tea_Rst = new int [Tea_Num + 1][30 + 1];
        for (i = 0; i <= Tea_Num; i++)
        {
            for (j = 0; j <= 30; j++)
            {
                Tea_Rst[i][j] = 0;
            }
        }

        dgl = new int[m + 1];
        for (i = 1; i <= m; i++)
        {
            dgl[i] = 0;
        }
        PDay = new int[m + 1];
        PCr = new int[m + 1];
        All_cl = new int[m + 1];
        MCrs = new int[l];
        MCls = new int[l];
        Min_Day = new int[l];
        Max_Day = new int[l];
        Max_Mr = new int[l];
        Max_Af = new int[l];
        Crs_Attri = new int[l];
        LTK_Week = new int[l];
        Class_Score = new int[l];
        Tea_JP_Score = new int[Tea_Num + 1];
        Tea_Score = new int[Tea_Num + 1];
        TScr = new int[Day_Crs + 1];
        Tea_Scr = new int [Tea_Num + 1][Weak_Day + 1];
        //KM_Tea = new int[ l ];
        int t1, t2, t3;
        t1 = 1;
        for (t2 = 1; t2 <= Weak_Day; t2++)
            for (t3 = 1; t3 <= Day_Crs; t3++)
            {
                PDay[t1] = t2;
                PCr[t1] = t3;
                t1++;
            }
        Vtx_len = 1;
        for (i = 1; i <= Class_Num; i++)
            for (j = 1; j <= Crs_Num; j++)
                Vtx_len += Class_Crs_Num[i][j];

        YPK_Num = new int[l + 1];
        for (i = 0; i <= l; i++)
        {
            YPK_Num[i] = 0;
        }
        YPK = new int[Vtx_len + 1];
        for (i = 0; i <= Vtx_len; i++)
        {
            YPK[i] = 0;
        }
        AVB_Cl = new int[Vtx_len+1][m];

        Color = new int[Vtx_len + 1];
        Initial_Color = new int[Vtx_len + 1];
        Best_Color= new int[Vtx_len + 1];
        Final_Color = new int[Vtx_len + 1];
        Par = new int[Vtx_len];
        A_Matrix = new int[Vtx_len + 1][Vtx_len + 1];
        Synthesis_Node = new int[Vtx_len + 1];

        Set_cl = new int [l + 1][m];
        Set_Crs = new int[Class_Num + 1][Crs_Num + 1][m];

        Crs_srt_cl = new int [l + 1][m];

        Node_Tea = new int[Vtx_len + 1][Tea_Num + 1];//教师属性
        Node_Stu = new int[Vtx_len + 1][Class_Num*Class_Num + 1];//学生属性
        Node_Course = new int[Vtx_len + 1][2 + 1];//课程属性

        Node_Class = new int[Vtx_len + 1][Class_Num + 1];//班级属性
        Node_Avb = new int[Vtx_len + 1][m];

        Edge = new int[Vtx_len + 1][Vtx_len + 1];
        Node_Tea_Sign = new int[Vtx_len + 1];
        Day_Set = new int[Weak_Day + 1];
        set = new int[30];

        JP_KC = new int [30+1][m+1];
        for (i = 0; i <= 30; i++)
        {
            for (j = 0; j <= m; j++)
            {
                JP_KC[i][j] = 0;
            }
        }

        daycr = new int[Weak_Day + 1];
        daylt = new int[Weak_Day + 1];

        tabuh = new int[1500];
        best_x = new int[1000];
        best_v = new int[1000];
        TTL = 2 * Class_Num;
        TabuTenure = new int[Vtx_len + 1][m + 1];
        Delta_Matrix = new int[Vtx_len + 1][m + 1];
        Delta_Crs = new int[Vtx_len + 1][m + 1];
        Delta_Tpj = new int[Vtx_len + 1][m + 1];
        Delta_Ttm = new int[Vtx_len + 1][m + 1];
        /////////////////////////////////////////////////////////////
        //不排课时间
        List<NoClassTimeInfo> noClassTimeInfoList = inputBasicData.getNoClassTimeInfo();
        for(NoClassTimeInfo noClassTimeInfo:noClassTimeInfoList)
        {
            if(co.compare(noClassTimeInfo.getType(),"Class") == 0)//班级不排课
            {
                for(j=0;j<Class.size();j++)
                {
                    if(Class.get(j) == noClassTimeInfo.getId() )
                    {
                        class_id = j + 1;//哪个班级
                        break;
                    }
                }
                List<TimeInfo> timeInfoList = noClassTimeInfo.getTimeInfo();
                for(TimeInfo timeInfo:timeInfoList)
                {
                    day = timeInfo.getDay();
                    List<Integer> avbTimeInfoList = timeInfo.getAvbTimeInfo();
                    for(k=0;k<avbTimeInfoList.size();k++)
                    {
                        Class_AvrT[class_id][day][k+1] = avbTimeInfoList.get(k);
                    }
                }

            }
            else if(co.compare(noClassTimeInfo.getType(),"Teacher") == 0)//教师不排课
            {
                for(j=0;j<Tea_Name.size();j++)
                {
                    if(co.compare(Tea_Name.get(j),noClassTimeInfo.getName()) == 0)
                    {
                        tea_id = j + 1;//哪个教师
                        break;
                    }
                }
                Tct[tea_id] = noClassTimeInfo.getConcentrated();
                List<TimeInfo> timeInfoList = noClassTimeInfo.getTimeInfo();
                for(TimeInfo timeInfo:timeInfoList)
                {
                    day = timeInfo.getDay();
                    List<Integer> avbTimeInfoList = timeInfo.getAvbTimeInfo();
                    for(k=0;k<avbTimeInfoList.size();k++)
                    {
                        Tea_AvrT[tea_id][day][k+1] = avbTimeInfoList.get(k);
                    }
                }
            }
            else if(co.compare(noClassTimeInfo.getType(),"Course") == 0)
            {
                for(j=0;j<CourseID.size();j++)
                {
                    if(CourseID.get(j) == noClassTimeInfo.getId() )
                    {
                        crs_id = j + 1;//哪门课程
                        break;
                    }
                }
                List<TimeInfo> timeInfoList = noClassTimeInfo.getTimeInfo();
                for(TimeInfo timeInfo:timeInfoList)
                {
                    day = timeInfo.getDay();
                    List<Integer> avbTimeInfoList = timeInfo.getAvbTimeInfo();
                    for(k=0;k<avbTimeInfoList.size();k++)
                    {
                        Crs_AvrT[crs_id][day][k + 1] = avbTimeInfoList.get(k);
                    }
                }
            }
        }

        //合班课
        HBK_Num = inputBasicData.getHBKNum();
        HBK = new int[Class_Num * 2 + 1][Class_Num + 1];
        HBK_C = new int[Class_Num * 2 + 1];
        if(HBK_Num>0)
        {
            List<HBKInfo> hbkInfoList = inputBasicData.getHbkInfo();
            i = 0;
            for(HBKInfo hbkInfo:hbkInfoList)
            {
                for(j=0;j<CourseID.size();j++)
                {
                    if(CourseID.get(j) == hbkInfo.getHbkcID())
                    {
                        crs_id = j + 1;//哪门课程
                    }
                }
                i++;
                HBK_C[i] = crs_id;
                HBK[i][0] = hbkInfo.getHbkClassNum();
                List<Integer> hbkClassInfoList = hbkInfo.getHbkClassInfo();
                for(j=0;j<hbkClassInfoList.size();j++)
                {
                    for(k=0;k<Class.size();k++)
                    {
                        if(Class.get(k) - hbkClassInfoList.get(j) == 0)
                        {
                            class_id = k+1;//哪个班
                            break;
                        }
                    }
                    HBK[i][j+1] = class_id;
                }
            }
        }
        //预排课
        List<YPKInfo> ypkInfoList = inputBasicData.getYpkInfo();
        for(YPKInfo ypkInfo:ypkInfoList)
        {
            for(j=0;j<Class.size();j++)
            {
                if(Class.get(j) == ypkInfo.getClassID())
                {
                    class_id = j + 1;//哪个班
                    break;
                }
            }
            List<YPKDataInfo> ypkDataInfoList = ypkInfo.getYpkDataInfo();
            for(YPKDataInfo ypkDataInfo:ypkDataInfoList)
            {
                day = ypkDataInfo.getDay();
                List<Integer> ypkDataList = ypkDataInfo.getYpkData();
                for(k=0;k<ypkDataList.size();k++)
                {
                    crs_id = 0;//初始化
                    for(x=0;x<CourseID.size();x++)
                    {
                        if (CourseID.get(x) - ypkDataList.get(k) == 0)
                        {
                            crs_id = x + 1;//哪门课
                            break;
                        }
                    }
                    Class_Crs[class_id][day][k + 1] = crs_id;
                }
            }
        }

    }


    public void setJsonData(String path){
        int i, j, k;
        int class_id = 0;
        int day = 0;
        int tea_id = 0;
        int crs_id = 0;
        int m,l;
        int x,y;
        Coursename = new ArrayList();
        CourseID = new ArrayList();
        Class = new ArrayList();
        Tea_Name = new ArrayList<>();
        Tea_ID = new ArrayList<>();
        String jsonStr = ReadFile(path);
        JSONObject jsonObject=JSONObject.fromObject(jsonStr);
        Weak_Day = jsonObject.getInt("weakDay");
        Day_Crs_Mr = jsonObject.getInt("dayCrsMr");
        Day_Crs_Af = jsonObject.getInt("dayCrsAf");
        Day_Crs = jsonObject.getInt("dayCrs");

        KCL = Weak_Day*Day_Crs;
        Class_Num = jsonObject.getInt("classNum");
        JSONArray classList = jsonObject.getJSONArray("classID");
        for(i = 0;i < classList.size();i++)
        {
            Class.add(classList.getInt(i));
        }
        Crs_Num = jsonObject.getInt("crsNum");
        JSONArray crsList = jsonObject.getJSONArray("crsListInfo");
        for(i = 0;i < crsList.size();i++)
        {
            CourseID.add(crsList.getJSONObject(i).getInt("courseID"));
            //System.out.print(crsList.getJSONObject(i).getInt("id") + "\n");
            Coursename.add(crsList.getJSONObject(i).getString("courseName"));
        }

        Tea_Num = jsonObject.getInt("teaNum");
        JSONArray teaList = jsonObject.getJSONArray("teaName");
        for(i = 0;i < teaList.size();i++)
        {
            Tea_Name.add(teaList.getString(i));
        }
        System.out.print("Crs_Num =" + Crs_Num + "\n");
        System.out.print("Class_Num =" + Class_Num + "\n");
        System.out.print("Tea_Num =" + Tea_Num + "\n");

        Class_Crs_Num = new int[Class_Num + 1][Crs_Num + 1];
        for (i = 0; i <= Class_Num; i++)
            for (j = 0; j <= Crs_Num; j++)
                Class_Crs_Num[i][j] = 0;

        Class_Crs_Num_L = new int[Class_Num + 1][Crs_Num + 1];
        for (i = 0; i <= Class_Num; i++)
            for (j = 0; j <= Crs_Num; j++)
                Class_Crs_Num_L[i][j] = 0;

        Class_Tea = new int[Class_Num + 1][Crs_Num + 1];
        for (i = 0; i <= Class_Num; i++)
            for (j = 0; j <= Crs_Num; j++)
                Class_Tea[i][j] = 0;
        Cct = new int[Crs_Num + 1];//优先排课
        for (i = 0; i <= Crs_Num; i++)
            Cct[i] = 0;
        Tct = new int[Tea_Num + 1];//教师集中排课
        for (i = 0; i <= Tea_Num; i++)
            Tct[i] = 1;
        Class_AvrT = new int [Class_Num + 1][Weak_Day + 1][Day_Crs + 1];
        Tea_AvrT = new int [Tea_Num + 1][Weak_Day + 1][Day_Crs + 1];
        Crs_AvrT = new int [Crs_Num + 1][Weak_Day + 1][Day_Crs + 1];
        ////////////////////////////////////////////////////////
        for (i = 0; i <= Class_Num; i++)
        {
            for (j = 0; j <= Weak_Day; j++)
            {
                for (k = 0; k <= Day_Crs; k++)
                    Class_AvrT[i][j][k] = 1;
            }
        }
        for (i = 0; i <= Tea_Num; i++)
        {
            for (j = 0; j <= Weak_Day; j++)
            {
                for (k = 0; k <= Day_Crs; k++)
                    Tea_AvrT[i][j][k] = 1;
            }
        }
        for (i = 0; i <= Crs_Num; i++)
        {
            for (j = 0; j <= Weak_Day; j++)
            {
                for (k = 0; k <= Day_Crs; k++)
                    Crs_AvrT[i][j][k] = 1;
            }
        }

        Ctwo = jsonObject.getInt("ctwo");
        if(Ctwo == 1)//触发优先排课
        {
            JSONArray CtwoCrsArray = jsonObject.getJSONArray("ctwoCrs");
            for(i=0;i<CtwoCrsArray.size();i++)
            {
                for(j=0;j<CourseID.size();j++)
                {
                    if(CourseID.get(j) - CtwoCrsArray.getInt(i) == 0)
                    {
                        crs_id = j + 1;
                        break;
                    }
                }
                Cct[crs_id] = 1;
            }
            C_Time = jsonObject.getInt("ctwoTime");
        }

        JSONArray classArray = jsonObject.getJSONArray("classCrsInfo");
        for(i = 0;i<classArray.size();i++)
        {
            for(j=0;j<Class.size();j++)
            {
                if(Class.get(j) == classArray.getJSONObject(i).getInt("classID") )
                {
                    class_id = j + 1;//哪个班级
                    break;
                }
            }

            //这里有问题
            JSONArray courseArray = classArray.getJSONObject(i).getJSONArray("courseInfoList");
            for(j=0;j<courseArray.size();j++)
            {

                for(x=0;x<Tea_Name.size();x++)
                {
                    if(co.compare(Tea_Name.get(x),courseArray.getJSONObject(j).getString("courseTeaName")) == 0)
                    {
                        tea_id = x + 1;
                        break;
                    }
                }
                for(x=0;x<CourseID.size();x++)
                {
                    if(CourseID.get(x) == courseArray.getJSONObject(j).getInt("courseID"))
                    {
                        crs_id = x + 1;
                        break;
                    }
                }
                Class_Crs_Num[class_id][crs_id] = courseArray.getJSONObject(j).getInt("courseNum");
                Class_Crs_Num_L[class_id][crs_id] = courseArray.getJSONObject(j).getInt("courseLTNum");
                Class_Tea[class_id][crs_id] = tea_id;
                //System.out.println(Class_Tea[class_id][crs_id]);
            }
        }

        for(i=1;i<=Class_Num;i++)
        {
            for(j=1;j<=Crs_Num;j++)
            {
                System.out.print(Class_Tea[i][j] + " ");
            }
            System.out.print("\n");
        }
        //////////////////////////////////////////////////////////////
        Class_Crs_HEBAN = new int[Class_Num + 1][Crs_Num + 1];

        for (i = 1; i <= Class_Num; i++)
            for (j = 1; j <= Crs_Num; j++)
                Class_Crs_HEBAN[i][j] = i;

        Class_Crs_CD = new int[Class_Num + 1][Crs_Num + 1];
        Tea_CP = new int [Tea_Num + 1][Class_Num + 1];//教师教了哪几个班？
        Tea_CP_Sign = new int [Tea_Num + 1][Class_Num + 1];//教师教了哪几个班？
        Class_Crs = new int [Class_Num + 1][Weak_Day + 1][Day_Crs + 1];
        Tea_Crs = new int [Tea_Num + 1][Weak_Day + 1][Day_Crs + 1];
        for (i = 0; i <= Class_Num; i++)
        {
            for (j = 0; j <= Weak_Day; j++)
            {
                for (k = 0; k <= Day_Crs; k++)
                    Class_Crs[i][j][k] = 0;
            }
        }
        for (i = 0; i <= Tea_Num; i++)
        {
            for (j = 0; j <= Weak_Day; j++)
            {
                for (k = 0; k <= Day_Crs; k++)
                    Tea_Crs[i][j][k] = 0;
            }
        }
        Class_Crs_Rst = new int [Class_Num + 1][Weak_Day + 1][Day_Crs + 1];
        Tea_Crs_Rst = new int [Tea_Num + 1][Weak_Day + 1][Day_Crs + 1];
        Class_Weak = new int [Class_Num*Crs_Num + 1][Weak_Day + 1][9 + 1];
        Tea_Weak = new int [Tea_Num + 1][Weak_Day + 1][5 + 1];

        l = Class_Num*Crs_Num + 1;//节点数
        m = Weak_Day*Day_Crs + 1;//着色数
        Class_Rst = new int [l + 1][30 + 1];
        for (i = 0; i <= l; i++)
        {
            for (j = 0; j <= 30; j++)
            {
                Class_Rst[i][j] = 0;
            }
        }
        Tea_Rst = new int [Tea_Num + 1][30 + 1];
        for (i = 0; i <= Tea_Num; i++)
        {
            for (j = 0; j <= 30; j++)
            {
                Tea_Rst[i][j] = 0;
            }
        }

        dgl = new int[m + 1];
        for (i = 1; i <= m; i++)
        {
            dgl[i] = 0;
        }
        PDay = new int[m + 1];
        PCr = new int[m + 1];
        All_cl = new int[m + 1];
        MCrs = new int[l];
        MCls = new int[l];
        Min_Day = new int[l];
        Max_Day = new int[l];
        Max_Mr = new int[l];
        Max_Af = new int[l];
        Crs_Attri = new int[l];
        LTK_Week = new int[l];
        Class_Score = new int[l];
        Tea_JP_Score = new int[Tea_Num + 1];
        Tea_Score = new int[Tea_Num + 1];
        TScr = new int[Day_Crs + 1];
        Tea_Scr = new int [Tea_Num + 1][Weak_Day + 1];
        //KM_Tea = new int[ l ];
        int t1, t2, t3;
        t1 = 1;
        for (t2 = 1; t2 <= Weak_Day; t2++)
            for (t3 = 1; t3 <= Day_Crs; t3++)
            {
                PDay[t1] = t2;
                PCr[t1] = t3;
                t1++;
            }
        Vtx_len = 1;
        for (i = 1; i <= Class_Num; i++)
            for (j = 1; j <= Crs_Num; j++)
                Vtx_len += Class_Crs_Num[i][j];

        YPK_Num = new int[l + 1];
        for (i = 0; i <= l; i++)
        {
            YPK_Num[i] = 0;
        }
        YPK = new int[Vtx_len + 1];
        for (i = 0; i <= Vtx_len; i++)
        {
            YPK[i] = 0;
        }
        AVB_Cl = new int[Vtx_len+1][m];

        Color = new int[Vtx_len + 1];
        Initial_Color = new int[Vtx_len + 1];
        Best_Color= new int[Vtx_len + 1];
        Final_Color = new int[Vtx_len + 1];
        Par = new int[Vtx_len];
        A_Matrix = new int[Vtx_len + 1][Vtx_len + 1];
        Synthesis_Node = new int[Vtx_len + 1];

        Set_cl = new int [l + 1][m];
        Set_Crs = new int[Class_Num + 1][Crs_Num + 1][m];

        Crs_srt_cl = new int [l + 1][m];

        Node_Tea = new int[Vtx_len + 1][Tea_Num + 1];//教师属性
        Node_Stu = new int[Vtx_len + 1][Class_Num*Class_Num + 1];//学生属性
        Node_Course = new int[Vtx_len + 1][2 + 1];//课程属性

        Node_Class = new int[Vtx_len + 1][Class_Num + 1];//班级属性
        Node_Avb = new int[Vtx_len + 1][m];

        Edge = new int[Vtx_len + 1][Vtx_len + 1];
        Node_Tea_Sign = new int[Vtx_len + 1];
        Day_Set = new int[Weak_Day + 1];
        set = new int[30];

        JP_KC = new int [30+1][m+1];
        for (i = 0; i <= 30; i++)
        {
            for (j = 0; j <= m; j++)
            {
                JP_KC[i][j] = 0;
            }
        }

        daycr = new int[Weak_Day + 1];
        daylt = new int[Weak_Day + 1];

        tabuh = new int[1500];
        best_x = new int[1000];
        best_v = new int[1000];
        TTL = 2 * Class_Num;
        TabuTenure = new int[Vtx_len + 1][m + 1];
        Delta_Matrix = new int[Vtx_len + 1][m + 1];
        Delta_Crs = new int[Vtx_len + 1][m + 1];
        Delta_Tpj = new int[Vtx_len + 1][m + 1];
        Delta_Ttm = new int[Vtx_len + 1][m + 1];
        /////////////////////////////////////////////////////////////
        //不排课时间
        JSONArray noClassTimeArray = jsonObject.getJSONArray("noClassTimeInfo");
        for(i = 0;i<noClassTimeArray.size();i++)
        {
            //System.out.print(noClassTimeArray.getJSONObject(i).getString("type"));
            if(co.compare(noClassTimeArray.getJSONObject(i).getString("type"),"Class") == 0)
            {
                for(j=0;j<Class.size();j++)
                {
                    if(Class.get(j) == noClassTimeArray.getJSONObject(i).getInt("id") )
                    {
                        class_id = j + 1;//哪个班级
                    }
                }
                JSONArray timeArray = noClassTimeArray.getJSONObject(i).getJSONArray("timeInfo");
                for(j=0;j<timeArray.size();j++)
                {
                    day = timeArray.getJSONObject(j).getInt("day");
                    JSONArray avbTimeArray = timeArray.getJSONObject(j).getJSONArray("avbTimeInfo");
                    for(k=0;k<avbTimeArray.size();k++)
                    {
                        Class_AvrT[class_id][day][k+1] = avbTimeArray.getInt(k);
                        //System.out.print(Class_AvrT[class_id][day][k+1] + " ");
                    }
                }
            }
            else if(co.compare(noClassTimeArray.getJSONObject(i).getString("type"),"Teacher") == 0)
            {
                for(j=0;j<Tea_Name.size();j++)
                {
                    if(co.compare(Tea_Name.get(j),noClassTimeArray.getJSONObject(i).getString("name")) == 0)
                    {
                        tea_id = j + 1;//哪个教师
                    }
                }
                Tct[tea_id] = noClassTimeArray.getJSONObject(i).getInt("concentrated");
                JSONArray timeArray = noClassTimeArray.getJSONObject(i).getJSONArray("timeInfo");
                for(j=0;j<timeArray.size();j++)
                {
                    day = timeArray.getJSONObject(j).getInt("day");
                    JSONArray avbTimeArray = timeArray.getJSONObject(j).getJSONArray("avbTimeInfo");
                    for(k=0;k<avbTimeArray.size();k++)
                    {
                        Tea_AvrT[tea_id][day][k+1] = avbTimeArray.getInt(k);
                    }
                }
            }
            else if(co.compare(noClassTimeArray.getJSONObject(i).getString("type"),"Course") == 0)
            {
                for(j=0;j<CourseID.size();j++)
                {
                    if(CourseID.get(j) == noClassTimeArray.getJSONObject(i).getInt("id") )
                    {
                        crs_id = j + 1;//哪门课程
                    }
                }
                JSONArray timeArray = noClassTimeArray.getJSONObject(i).getJSONArray("timeInfo");
                for(j=0;j<timeArray.size();j++)
                {
                    day = timeArray.getJSONObject(j).getInt("day");
                    JSONArray avbTimeArray = timeArray.getJSONObject(j).getJSONArray("avbTimeInfo");
                    for(k=0;k<avbTimeArray.size();k++)
                    {
                        Crs_AvrT[crs_id][day][k+1] = avbTimeArray.getInt(k);
                    };
                }
            }
        }
        //合班课
        HBK_Num = jsonObject.getInt("hBKNum");
        HBK = new int[Class_Num * 2 + 1][Class_Num + 1];
        HBK_C = new int[Class_Num * 2 + 1];
        if(HBK_Num>0)
        {
            JSONArray HBKArray = jsonObject.getJSONArray("hbkInfo");
            for(i=0;i<HBKArray.size();i++)
            {
                for(j=0;j<CourseID.size();j++)
                {
                    if(CourseID.get(j) == HBKArray.getJSONObject(i).getInt("hbkcID") )
                    {
                        crs_id = j + 1;//哪门课程
                    }
                }
                HBK_C[i+1] = crs_id;
                HBK[i+1][0] = HBKArray.getJSONObject(i).getInt("hbkClassNum");
                JSONArray HBKClassArray = HBKArray.getJSONObject(i).getJSONArray("hbkClassInfo");
                for(j=0;j<HBKClassArray.size();j++)
                {
                    for(k=0;k<Class.size();k++)
                    {
                        if(Class.get(k) == HBKClassArray.getInt(j))
                        {
                            class_id = k+1;//哪个班
                            break;
                        }
                    }
                    HBK[i+1][j+1] = class_id;
                }
            }
        }
        //预排课
        JSONArray YPKArray = jsonObject.getJSONArray("ypkInfo");
        for(i=0;i<YPKArray.size();i++)
        {
            for(j=0;j<Class.size();j++)
            {
                if(Class.get(j) == YPKArray.getJSONObject(i).getInt("classID"))
                {
                    class_id = j + 1;//哪个班
                    break;
                }
            }
            JSONArray timeArry = YPKArray.getJSONObject(i).getJSONArray("ypkDataInfo");
            for(j=0;j<timeArry.size();j++) {
                day = timeArry.getJSONObject(j).getInt("day");
                JSONArray YPKTimeArray = timeArry.getJSONObject(j).getJSONArray("ypkData");
                for (k = 0; k < YPKTimeArray.size(); k++) {
                    crs_id = 0;//初始化
                    for(x=0;x<CourseID.size();x++)
                    {
                        if (CourseID.get(x) == YPKTimeArray.getInt(k)) {
                            crs_id = x + 1;//哪门课
                        }
                    }
                    //System.out.print(crs_id + "\n");
                    Class_Crs[class_id][day][k + 1] = crs_id;
                }
            }
        }
    }

    private static String ReadFile(String path){
        String laststr="";
        File file=new File(path);// 打开文件
        BufferedReader reader=null;
        try{
            FileInputStream in = new FileInputStream(file);
            reader=new BufferedReader(new InputStreamReader(in,"UTF-8"));// 读取文件
            String tempString=null;
            while((tempString=reader.readLine())!=null){
                laststr=laststr+tempString;
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(reader!=null){
                try{
                    reader.close();
                }catch(IOException el){
                }
            }
        }
        return laststr;
    }

    private void check_kc()throws MyException
    {
        int i, j, l, m, n, ll, t;
        for (i = 1; i <= Class_Num; i++)
        {
            l = 0;
            for (j = 1; j <= Crs_Num; j++)
                l += Class_Crs_Num[i][j];
            ll = 0;
            for (j = 1; j <= KCL; j++)
            {
                m = PDay[j];
                n = PCr[j];
                ll += Class_AvrT[i][m][n];
                //System.out.print(Class_AvrT[i][m][n] + " ");
            }
            if (l > ll)
            {
                System.out.print("班级 " + i + " 课程数量超标，请检查 l = " + l + " 可排课时间为"  + ll + "\n" );
                throw new MyException( "班级 " + i + " 课程数量超标，请检查 l = " + l + " 可排课时间为"  + ll);
            }
        }

        for (t = 1; t <= Tea_Num; t++)
        {
            l = 0;
            for (i = 1; i <= Class_Num; i++)
                for (j = 1; j <= Crs_Num; j++)
                    if (Class_Tea[i][j] == t)
                        l += Class_Crs_Num[i][j];
            ll = 0;
            for (j = 1; j <= KCL; j++)
            {
                m = PDay[j];
                n = PCr[j];
                ll += Tea_AvrT[t][m][n];
            }
            //System.out.print("ll=" + ll + "\n");
            //System.out.print("l=" + l + "\n");
            if (l > ll)
            {
                System.out.print("老师 " + Tea_Name.get(t-1) + "课程数量超标，请检查" + "\n");
                throw new MyException("老师 " + Tea_Name.get(t-1) + "课程数量超标，请检查l = " + l + " 可排课时间为"  + ll);
            }
        }

    }
    private void check_HBK()throws MyException
    {
        int i, j, l, m, n;
        for (i = 1; i <= HBK_Num; i++)
        {
            l = HBK[i][0];
            m = Class_Tea[HBK[i][1]][HBK_C[i]];
            for (j = 2; j <= l; j++)
            {
                n = Class_Tea[HBK[i][j]][HBK_C[i]];
                if (m != n)
                {
                    System.out.print("存在合班课老师不一致的情况");
                    throw new MyException("存在合班课老师不一致的情况");
                }
            }
        }
    }

    private void set_HB(){
        int j, k, l, m, bn;
        //设置合班课
        for (bn = 1; bn <= HBK_Num; bn++)
        {
            m = HBK[bn][1];
            k = HBK_C[bn];
            l = HBK[bn][0];

            Class_Crs_HEBAN[m][k] = 100000 + bn;
            for (j = 2; j <= l; j++)
            {
                m = HBK[bn][j];
                Class_Crs_Num[m][k] = 0;
            }
        }

    }//设置合班课

    private void set_CD(){
        int i, j, l;
        l = 0;
        for (i = 1; i <= Class_Num; i++)
            for (j = 1; j <= Crs_Num; j++)
            {
                //System.out.print("Class_Crs_Num[i][j]=" + Class_Crs_Num[i][j] + "j=" + j + "\n");
                if (Class_Crs_Num[i][j]>0)//经过处理之后还需要排的课
                {
                    l++;
                    Class_Crs_CD[i][j] = l;
                    MCls[l] = i;//是哪个班
                    //System.out.print("MCls[l]=" + MCls[l] + " ");
                    MCrs[l] = j;//是哪门课
                    //System.out.print("MCrs[l]=" + MCrs[l] + "\n");
                }
                else
                    Class_Crs_CD[i][j] = -1;
            }
        MNum = l;
    }
    private void set_YPK(){
        int i, j, k, n;
        for (i = 1; i <= N_vtx; i++)
        {
            Initial_Color[i] = 0;
        }
        for (i = 1; i <= Class_Num; i++)
        {
            for (j = 1; j <= Weak_Day; j++)
            {
                for (k = 1; k <= Day_Crs; k++)
                {
                    if (Class_Crs[i][j][k] != 0)
                    {
                        //System.out.print("n=" + Class_Crs[i][j][k] + "\n");
                        n = Class_Crs[i][j][k];
                        YPK_Num[Class_Crs_CD[i][n]]++;
                        //System.out.print("Set_cl[Class_Crs_CD[i][n]=" + Set_cl[Class_Crs_CD[i][n]][YPK_Num[Class_Crs_CD[i][n]]] + "\n");
                        YPK[Set_cl[Class_Crs_CD[i][n]][YPK_Num[Class_Crs_CD[i][n]]]] = 1;
                        Initial_Color[Set_cl[Class_Crs_CD[i][n]][YPK_Num[Class_Crs_CD[i][n]]]] = (j - 1)*Day_Crs + k;
                        //System.out.print("Color=" + ((j - 1)*Day_Crs + k) + "\n");
                    }
                }
            }
        }
    }
    private void set_avbtime(){
        int i, j, k, m, n, gcls;
        int k1, k2, k3;

        for (i = 1; i <= N_vtx; i++)
        {
            for (j = 1; j <= KCL; j++)
            {
                k1 = k2 = k3 = 1;
                m = PDay[j];
                n = PCr[j];
                //班级不排课时间
                for (k = 1; k <= Node_Class[i][0]; k++)
                {
                    gcls = Node_Class[i][k];
                    k1 = k1&Class_AvrT[gcls][m][n];

                }
                //教师不排课时间
                for (k = 1; k <= Node_Tea[i][0]; k++)
                {
                    if (Node_Tea[i][k] >= 0)
                    {
                        k2 = k2&Tea_AvrT[Node_Tea[i][k]][m][n];
                    }
                    else if (Node_Tea[i][k] == -1)
                    {
                        k2 = 1;
                    }
                }
                //课程不排课时间
                for (k = 1; k <= Node_Course[i][0]; k++)
                {
                    k3 = k3&Crs_AvrT[Node_Course[i][k]][m][n];
                }
                k = k1&k2&k3;
                AVB_Cl[i][j] = k;
            }
        }
    }
    private void set_crs_attrib(){
        int l, gcls, gcr, nl, nt, min, max;
        l = 0;

        for (gcls = 1; gcls <= Class_Num; gcls++)
            for (gcr = 1; gcr <= Crs_Num; gcr++)
            {
                if (Class_Crs_Num[gcls][gcr]>0)
                {
                    l++;
                    nl = Class_Crs_Num[gcls][gcr];//总的课时数
                    nt = Class_Crs_Num_L[gcls][gcr];//连堂课
                    if (nt == 0)
                    {
                        max = (nl - 1) / Weak_Day + 1;
                        min = nl / Weak_Day;
                        Max_Day[l] = max;
                        Min_Day[l] = min;
                        if(nl>3)
                        {
                            Max_Mr[l] = Class_Crs_Num[gcls][gcr]-1;
                            Max_Af[l] = Class_Crs_Num[gcls][gcr]-1;
                        }
                        else
                        {
                            Max_Mr[l] = Class_Crs_Num[gcls][gcr];
                            Max_Af[l] = Class_Crs_Num[gcls][gcr];
                        }
                        LTK_Week[l] = 0;
                        Crs_Attri[l] = 0;
                        if ( co.compare(Coursename.get(gcr-1),"语文") == 0 || co.compare(Coursename.get(gcr-1),"数学") == 0 || co.compare(Coursename.get(gcr-1),"英语") == 0 )
                            Crs_Attri[l] = 1;//一等课
                        if ((nl == 2) && (Weak_Day >= 4))
                            Crs_Attri[l] = 2;//两天不能连排
                        else if ((nl == 3) && (Weak_Day > 4))
                            Crs_Attri[l] = 3;//三天不能连排
                    }
                    else
                    {
                        max = (nl - 2 * nt - 1) / (Weak_Day - nt) + 1;
                        min = (nl - 2 * nt) / (Weak_Day - nt);
                        Max_Day[l] = max;
                        Min_Day[l] = min;
                        if(nl>3)
                        {
                            Max_Mr[l] = Class_Crs_Num[gcls][gcr]-1;
                            Max_Af[l] = Class_Crs_Num[gcls][gcr]-1;
                        }
                        else
                        {
                            Max_Mr[l] = Class_Crs_Num[gcls][gcr];
                            Max_Af[l] = Class_Crs_Num[gcls][gcr];
                        }
                        LTK_Week[l] = nt;
                        Crs_Attri[l] = 0;
                        if (co.compare(Coursename.get(gcr-1),"语文") == 0 || co.compare(Coursename.get(gcr-1),"数学") == 0 || co.compare(Coursename.get(gcr-1),"英语") == 0)
                            Crs_Attri[l] = 1;
                        if ((nl - nt == 2) && (Weak_Day >= 4))
                            Crs_Attri[l] = 2;//两天不能连排
                        else if ((nl - nt == 3) && (Weak_Day > 4))
                            Crs_Attri[l] = 3;//三天不能连排
                    }
                }
            }
    }
    private int has_common_node(int t1, int t2){
        int i, j;
        for (i = 1; i <= Node_Class[t1][0]; i++)//班级冲突冲突
        {
            for (j = 1; j <= Node_Class[t2][0]; j++)
            {
                if (Node_Class[t1][i] == Node_Class[t2][j])
                    return 1;
            }
        }
        /*
        for (i = 1; i <= Node_Stu[t1][0]; i++)//学生冲突
        {
            if (Node_Stu[t1][i] == 0)
            {
                continue;
            }
            for (j = 1; j <= Node_Stu[t2][0]; j++)
            {
                if (Node_Stu[t2][j] == 0)
                    continue;
                if (Node_Stu[t1][i] == Node_Stu[t2][j])
                    return 1;
            }
        }
        */
        for (i = 1; i <= Node_Tea[t1][0]; i++)//学生冲突
        {
            if (Node_Tea[t1][i] == 0)
            {
                continue;
            }
            if (Node_Tea[t1][i] == -1)
                return 0;
            for (j = 1; j <= Node_Tea[t2][0]; j++)
            {
                if (Node_Tea[t2][j] == 0)
                    continue;
                if (Node_Tea[t2][j] == -1)
                    return 0;
                if (Node_Tea[t1][i] == Node_Tea[t2][j])
                    return 1;
            }
        }
        return 0;
    }
    private void con_struct_graph(){
        int i, j, k, l, m, n, t, th,x,y;
        int bn;
        l = 1;
        k = 1;
        for (i = 1; i <= Class_Num; i++)
        {
            for (j = 1; j <= Crs_Num; j++)
                if (Class_Crs_Num[i][j]>0)
                {
                    Set_cl[k][0] = Class_Crs_Num[i][j];
                    for (t = 1; t <= Class_Crs_Num[i][j]; t++)
                    {
                        Set_cl[k][t] = l;
                        Par[l] = k;
                        l++;
                    }
                    k++;
                }
        }
        N_vtx = l - 1;
        //System.out.print("N_vtx=" + N_vtx + "\n");
        for (i = 1; i <= N_vtx; i++)
        {
            m = MCls[Par[i]];//哪个班
            n = MCrs[Par[i]];//哪门课
            t = Class_Tea[m][n];
            Node_Tea_Sign[i] = t;//哪个老师
        }
        /*
        for (i = 1; i <= N_vtx; i++)
        {
            System.out.print( Node_Tea_Sign[i] + " " );
        }
        */
        //**************************************开始设置图着色属性
        l = 1;
        for (i = 1; i <= Class_Num; i++)
        {
            for (j = 1; j <= Crs_Num; j++)
                if (Class_Crs_Num[i][j]>0)
                {
                    for (t = 1; t <= Class_Crs_Num[i][j]; t++)
                    {
                        th = Class_Tea[i][j];//作为标记
                        if (Class_Crs_HEBAN[i][j] >= 100000){//触发合班
                            bn = Class_Crs_HEBAN[i][j] - 100000;
                            //System.out.print("bn=" + bn + "\n");
                            Node_Class[l][0] = HBK[bn][0];
                            //System.out.print("HBK=" + HBK[bn][0] + "\n");
                            for (k = 1; k <= HBK[bn][0]; k++)
                            {
                                Node_Class[l][k] = HBK[bn][k];
                                //System.out.print("Node_Class=" + HBK[bn][0] + "\n");
                            }
                            Node_Course[l][0] = 1;
                            Node_Course[l][1] = j;
                            Node_Tea[l][0] = 1;
                            Node_Tea[l][1] = th;
                        }
                        else if (th == -200)//单双周
                        {
                            Node_Class[l][0] = 1;
                            Node_Class[l][1] = i;
                            Node_Course[l][0] = 1;
                            Node_Course[l][1] = j;
                            Node_Tea[l][0] = 2;
                            Node_Tea[l][1] = Class_Tea[i][DSZ[t][1]];
                            Node_Tea[l][2] = Class_Tea[i][DSZ[t][2]];

                        }
                        else//普通课 ，没问题
                        {
                            Node_Class[l][0] = 1;
                            Node_Class[l][1] = i;
                            Node_Tea[l][0] = 1;
                            Node_Tea[l][1] = th;
                            Node_Course[l][0] = 1;
                            Node_Course[l][1] = j;
                        }
                        l++;
                    }
                }
        }
        System.out.print("l=" + l + "\n");
        //**************************输出看一下
	/*
	for(i=1;i<=N_vtx;i++)
	{
	cout<<"Node_Class="<<Node_Class[i]<<endl;
	}
	for(i=1;i<=N_vtx;i++)
	{
	    cout<<"Node_Course="<<" ";
	    for(j=1;j<=Node_Course[i][0];j++)
	    {
	        cout<<Node_Course[i][j]<<" ";
	    }
	    cout<<endl;
	}
	for(i=1;i<=N_vtx;i++)
	{
	    cout<<"Node_Tea="<<" ";
	    for(j=1;j<=Node_Tea[i][0];j++)
	    {
	        cout<<Node_Tea[i][j]<<" ";
	    }
	    cout<<endl;
	    }
    for(i=1;i<=N_vtx;i++)
    {
	    cout<<"Node_Stu="<<" ";
	    for(j=1;j<=Node_Stu[i][0];j++)
	    {
	        cout<<Node_Stu[i][j]<<" ";
	    }
	    cout<<endl;
	}
	*/
        for (i = 1; i <= N_vtx; i++)
            for (j = 1; j <= N_vtx; j++)
            {
                Edge[i][j] = has_common_node(i, j);
            }

        for (i = 1; i <= N_vtx; i++)
            Edge[i][i] = 0;

        for (i = 1; i <= N_vtx; i++)
        {
            A_Matrix[i][0] = 0;
            for (j = 1; j <= N_vtx; j++)
            {
                if (Edge[i][j] == 1)
                {
                    A_Matrix[i][0]++;
                    A_Matrix[i][A_Matrix[i][0]] = j;
                }
            }
        }

        Synthesis_Node_Num = 0;//需要优先排的点
        for (i = 1; i <= N_vtx; i++)
        {
            if (A_Matrix[i][0] > 3 * KCL)
            {
                Synthesis_Node_Num++;
                Synthesis_Node[Synthesis_Node_Num] = i;
            }

        }
        /*
        for (i = 1; i <= Synthesis_Node_Num; i++)
        {
            System.out.print(Synthesis_Node[i] + "\n");
        }
        */
        //*************************************************教师教了哪几门课
        for (i = 0; i <= Tea_Num; i++)
            Tea_CP[i][0] = 0;
        for (i = 1; i <= N_vtx; i++)
        {
            for (j = 1; j <= Node_Tea[i][0]; j++)
            {
                if (Node_Tea[i][j] > 0 && Class_Crs_Num[MCls[Par[i]]][MCrs[Par[i]]] > 1)//教师不为0//单双周的其实是不计的
                {
                    l = 1;
                    for (k = 1; k <= Tea_CP[Node_Tea[i][j]][0]; k++)
                    {
                        if (Tea_CP[Node_Tea[i][j]][k] == Par[i])
                        {
                            l = 0;
                            break;
                        }
                    }
                    if (l == 1)
                    {
                        t = ++Tea_CP[Node_Tea[i][j]][0];
                        Tea_CP[Node_Tea[i][j]][t] = Par[i];
                    }

                }
            }
        }
        /*
        for (i = 1; i <= Tea_Num; i++)
        {
            for (j = 0; j <= Tea_CP[i][0]; j++)
                System.out.print(Tea_CP[i][j] + " ");
            System.out.print("\n");
        }
        */
        //************************************************教师教了几门课标记
        l = 1;
        for (i = 0; i <= Tea_Num; i++)
            Tea_CP_Sign[i][0] = 0;
        for (i = 1; i <= Tea_Num; i++)
        {
            Tea_CP_Sign[i][1] = 1;
            Tea_CP_Sign[i][0]++;
            for (j = 2; j <= Tea_CP[i][0]; j++)
            {

                for (k = 1; k < j; k++)
                {
                    l = 0;
                    if (MCrs[Tea_CP[i][j]] == MCrs[Tea_CP[i][k]] && Class_Crs_Num[MCls[Tea_CP[i][k]]][MCrs[Tea_CP[i][k]]] == Class_Crs_Num[MCls[Tea_CP[i][j]]][MCrs[Tea_CP[i][j]]])
                    {
                        Tea_CP_Sign[i][j] = Tea_CP_Sign[i][k];
                        l = 1;
                        break;
                    }
                }
                if (l == 0)
                {
                    Tea_CP_Sign[i][0]++;
                    Tea_CP_Sign[i][j] = Tea_CP_Sign[i][0];
                }
            }
            if (Tea_CP[i][0] == 0)
            {
                Tea_CP_Sign[i][0] = 0;
            }
        }
        /*
        for (i = 1; i <= Tea_Num; i++)
        {
            for (j = 0; j <= Tea_CP[i][0]; j++)
                System.out.print(Tea_CP_Sign[i][j]+" ");
            System.out.print("\n");
        }
        */
        //Set_crs
        for (i = 0; i <= Class_Num; i++)
        {
            for (j = 0; j <= Crs_Num; j++)
            {
                Set_Crs[i][j][0] = 0;
            }
        }

        for (i = 1; i <= Class_Num; i++)
        {
            for (j = 1; j <= Crs_Num; j++)
            {
                l = 1;
                for (x = 1; x <= N_vtx; x++)
                {
                    for (y = 1; y <= Node_Course[x][0]; y++)
                    {
                        if (Node_Course[x][y] == j && Node_Class[x][1] == i )
                        {
                            Set_Crs[i][j][l] = x;
                            l++;
                        }
                    }
                }
                Set_Crs[i][j][0] = l - 1;
            }
        }
        /*
        for (i = 1; i <= Class_Num; i++) {
            for (j = 1; j <= Crs_Num; j++) {
                for (k = 0; k <= Set_Crs[i][j][0]; k++) {
                    System.out.print(Set_Crs[i][j][k] + " ");
                }
                System.out.print("\n");
            }
            System.out.print("\n");
        }
        */
    }

    private void ini_tabu(){
        int i, j, l, l1;
        int[] al = { 1, 2, 1, 4, 1, 2, 1, 8, 1, 2, 1, 4, 1, 2, 1 };
        l1 = 0;
        for (i = 0; i < 15; i++)
        {
            l = l1 + 100;
            for (j = l1; j < l; j++)
                tabuh[j] = al[i] * TTL;
            l1 = l;
        }
    }
    private int Get_Initial_solution(int search_depth) throws MyException {
        int i;
        int num_best;
        int x, v;
        int iter;
        int best_delta, delt;
        int old_color;//移动前着色类
        int select;//随机选择方案
        //int num;//可大致认为是邻域总数
        greed_ini_solution();
        f_best = f;//f_best为当前的惩罚计数
        for (i = 1; i <= N_vtx; i++)
            Best_Color[i] = Color[i];

        for (i = 1; i <= N_vtx; i++)
            Best_Color[i] = Color[i];
        for (iter = 1; iter <= search_depth; iter++)
        {
            best_delta = 9999999;
            num_best = 0;
            for (x = 1; x <= N_vtx; x++)//每一个点
            {
                if (Node_Tea_Sign[x] != -2  && YPK[x] == 0)
                    if (Delta_Matrix[x][Color[x]] > 0) //
                    {
                        for (v = 1; v <= KCL; v++)
                            if ((AVB_Cl[x][v] == 1) && (v != Color[x]) && (TabuTenure[x][v] <= iter))
                            {
                                delt = Delta_Matrix[x][v] - Delta_Matrix[x][Color[x]];
                                if (delt < best_delta)
                                {
                                    best_x[0] = x;
                                    best_v[0] = v;
                                    num_best = 1;
                                    best_delta = delt;
                                }
                                else if (delt == best_delta)
                                {
                                    if (num_best < 1000)
                                    {
                                        best_x[num_best] = x;
                                        best_v[num_best] = v;
                                        num_best++;
                                    }
                                }
                            }
                    }
            }
            if (num_best == 0)
                return f_best;

            select = rand.nextInt(num_best);
            if (best_v[select] > 0)
            {
                old_color = Color[best_x[select]];  //存储顶点移动之前的着色类
                f += Delta_Matrix[best_x[select]][best_v[select]] - Delta_Matrix[best_x[select]][old_color];
                One_Move_Update_Delta_Matrix(best_x[select], old_color, best_v[select]);//更新增量矩阵
                Color[best_x[select]] = best_v[select];//更新着色类方案
                TabuTenure[best_x[select]][old_color] = 15 + iter;
            }

            if (f < f_best) //f improved ,update f_best and colour
            {
                f_best = f;//更新当前最优惩罚值
                for (i = 1; i <= N_vtx; i++)
                    Best_Color[i] = Color[i];
            }

            if (f == 0)
            {
                System.out.print("A feasible solution is found f == 0" + "\n");
                return 0;
            }
        }
        System.out.print("The solution is not feasible f == " + f + "\n");
        return f;
    }//搜索初始解

    private int Compoud_Tabu_Search_JP(int search_depth){
        int i, j, m, len, tl,l;
        int num_best;
        int x, y, v;
        int iter;
        int best_delta, delt;
        int v_best_delta, v_delt;
        int old_color, old_color_x, old_color_y;//移动前着色类
        int select;//随机选择方案
        int num;//可大致认为是邻域总数
        copy_ini_solution();
        f_best = f;//f_best为当前的惩罚计数

        f_crs = 0;
        //初始班级课表优劣
        for (i = 1; i <= MNum; i++)
        {
            m = Set_cl[i][1];//是哪一节课
            Class_Score[i] = Cal_Class_Score(m, Color[m]);
            f_crs += Class_Score[i];
        }
        //教师课表优劣

        Sort_Src();
        for (i = 1; i <= Tea_Num; i++)
            Tea_JP_Score[i] = 0;
        for (i = 1; i <= MNum; i++)
        {
            m = Set_cl[i][1];//是哪一节课
            for (j = 1; j <= Node_Tea[m][0]; j++)
            {
                if (Node_Tea[m][j] > 0)
                {
                    Tea_JP_Score[Node_Tea[m][j]] = Cal_JP_Score(m, Color[m], Node_Tea[m][j]);//哪位老师
                }
            }
        }

        f_tea = 0;
        for (i = 1; i <= Tea_Num; i++)
        {
            f_tea = f_tea + Tea_JP_Score[i];
        }

        fc = f_crs+ f_tea;
        fc_best = fc;
        System.out.print("f=" + f + " fc=" + fc + "\n");
        if ((f == 0) && (fc == 0))
        {
            System.out.print("finis f == 0 and fc == 0 and ftea == 0" + "\n");
            return 1;
        }
        System.out.print("In begin of Compoud_Tabu_Search_JP f = " + f + " fc = " + fc + "\n");
        Ini_Delta_Crs();
        Ini_Delta_Tpj();
        //当前最优解为当前解
        for (i = 1; i <= N_vtx; i++)
            Best_Color[i] = Color[i];
        for (iter = 1; iter <= search_depth; iter++)
        {
            num = 0;
            best_delta = 9999999;
            v_best_delta = 9999999;
            num_best = 0;
            for (x = 1; x <= N_vtx; x++)
            {

                l = 0;
                for (i = 1; i <= Node_Tea[x][0]; i++)
                {
                    if ((Node_Tea[x][i] > 0) && (Tea_JP_Score[Node_Tea[x][i]] > 0))
                    {
                        l = 1;
                    }
                }////教师是否>0

                if ((Delta_Matrix[x][Color[x]])>0 || (Class_Score[Par[x]])>0 || l == 1 )
                {
                    if (Node_Tea_Sign[x] != -2 && YPK[x] == 0)
                    {
                        for (v = 1; v <= KCL; v++)
                            if ((AVB_Cl[x][v] == 1) && (v != Color[x]) && (TabuTenure[x][v] <= iter))
                            {
                                delt = Delta_Matrix[x][v] - Delta_Matrix[x][Color[x]];
                                if (delt < best_delta)
                                {
                                    best_x[0] = x;
                                    best_v[0] = v;
                                    best_delta = delt;
                                    v_best_delta = Delta_Crs[x][v] + Delta_Tpj[x][v];// +3 * (PCr[v] - PCr[Color[x]]);
                                    num_best = 1;
                                }
                                else if (delt == best_delta)  //if some flips have the same delta,then choose at most 50 and record them   && num_best < 50
                                {
                                    v_delt = Delta_Crs[x][v] + Delta_Tpj[x][v];// +3 * (PCr[v] - PCr[Color[x]]);
                                    if (v_delt < v_best_delta)
                                    {
                                        best_x[0] = x;
                                        best_v[0] = v;
                                        best_delta = delt;
                                        v_best_delta = v_delt;
                                        num_best = 1;
                                    }
                                    else if ((v_delt == v_best_delta) && (num_best < 1000))
                                    {
                                        best_x[num_best] = x;
                                        best_v[num_best] = v;
                                        num_best++;
                                    }
                                }
                            }
                    }
                }
            }

            for (x = 1; x <= N_vtx; x++)
            {

                l = 0;
                for (i = 1; i <= Node_Tea[x][0]; i++)
                {
                    if ((Node_Tea[x][i] > 0) && (Tea_JP_Score[Node_Tea[x][i]] > 0))
                    {
                        l = 1;
                    }
                }////教师是否>0

                if ((Delta_Matrix[x][Color[x]])>0 || (Class_Score[Par[x]])>0 || l == 1)
                {
                    for (y = 1; y <= N_vtx; y++)
                        if ((MCls[Par[x]] == MCls[Par[y]]) && (AVB_Cl[x][Color[y]] == 1) && (AVB_Cl[y][Color[x]] == 1) && (Par[x] != Par[y]) && (Color[y] != Color[x]) && (TabuTenure[x][Color[y]] <= iter) && (TabuTenure[y][Color[x]] <= iter))
                        {
                            if ((Node_Tea_Sign[x] != -2) && (Node_Tea_Sign[y] != -2) && YPK[x] == 0 && YPK[y] == 0)
                            {
                                delt = Delta_Matrix[x][Color[y]] - Delta_Matrix[x][Color[x]] + Delta_Matrix[y][Color[x]] - Delta_Matrix[y][Color[y]] - 2;
                                if (delt < best_delta)
                                {
                                    best_x[0] = x;
                                    best_v[0] = -y;
                                    best_delta = delt;
                                    v_best_delta = Delta_Crs[x][Color[y]] + Delta_Crs[y][Color[x]] + Delta_Tpj[x][Color[y]] + Delta_Tpj[y][Color[x]];
                                    num_best = 1;
                                }
                                else if (delt == best_delta)  //if some flips have the same delta,then choose at most 50 and record them   && num_best < 50
                                {
                                    v_delt = Delta_Crs[x][Color[y]] + Delta_Crs[y][Color[x]] + Delta_Tpj[x][Color[y]] + Delta_Tpj[y][Color[x]];
                                    if (v_delt < v_best_delta)
                                    {
                                        best_x[0] = x;
                                        best_v[0] = -y;
                                        best_delta = delt;
                                        v_best_delta = v_delt;
                                        num_best = 1;
                                    }
                                    else if ((v_delt == v_best_delta) && (num_best < 1000))
                                    {
                                        best_x[num_best] = x;
                                        best_v[num_best] = -y;
                                        num_best++;
                                    }
                                }
                            }
                        }
                }
            }

            if (num_best > 0)
                select = rand.nextInt(num_best);   //从备选方案中随机选择一个方案
            else
                return fc_best;

            if (best_v[select] > 0)
            {
                old_color = Color[best_x[select]];  //存储顶点移动之前的着色类
                f += Delta_Matrix[best_x[select]][best_v[select]] - Delta_Matrix[best_x[select]][old_color];
                fc += Delta_Crs[best_x[select]][best_v[select]] +Delta_Tpj[best_x[select]][best_v[select]];

                One_Move_Update_Delta_Matrix(best_x[select], old_color, best_v[select]);//更新增量矩阵
                One_Move_Update_Course(best_x[select], old_color, best_v[select]);
                Color[best_x[select]] = best_v[select];//更新着色类方案
                One_Move_Update_JP(best_x[select]);
                tl = iter % 1500;
                TabuTenure[best_x[select]][Color[best_x[select]]] = tabuh[tl] + iter;
                Update_Delta_Crs(best_x[select]);
                Update_Delta_Tpj(best_x[select]);
            }
            else
            {
                x = best_x[select];
                y = -best_v[select];

                f += Delta_Matrix[x][Color[y]] - Delta_Matrix[x][Color[x]] + Delta_Matrix[y][Color[x]] - Delta_Matrix[y][Color[y]] - 2 * Edge[x][y];
                fc += Delta_Crs[x][Color[y]] + Delta_Crs[y][Color[x]] + Delta_Tpj[x][Color[y]] + Delta_Tpj[y][Color[x]];

                old_color_x = Color[x];  //存储顶点移动之前的着色类
                old_color_y = Color[y];
                One_Move_Update_Delta_Matrix(x, old_color_x, old_color_y);//更新增量矩阵
                One_Move_Update_Course(x, old_color_x, old_color_y);
                Color[x] = old_color_y;//更新着色类方案
                One_Move_Update_JP(x);
                tl = iter % 1500;
                TabuTenure[x][Color[x]] = tabuh[tl] + iter;

                One_Move_Update_Delta_Matrix(y, old_color_y, old_color_x);//更新增量矩阵
                One_Move_Update_Course(y, old_color_y, old_color_x);
                Color[y] = old_color_x;//更新着色类方案
                One_Move_Update_JP(y);
                TabuTenure[y][Color[y]] = tabuh[tl] + iter;

                Update_Delta_Crs(x);//没问题
                Update_Delta_Tpj(x);
                Update_Delta_Crs(y);
                Update_Delta_Tpj(y);

            }
            if (iter % 1000 == 0)
                reset_tabu_tenure();

            if (f < f_best) //f improved ,update f_best and colour
            {
                f_best = f;//更新当前最优惩罚值
                fc_best = fc;
                for (i = 1; i <= N_vtx; i++)
                    Best_Color[i] = Color[i];

            }
            else if ((f <= f_best) && (fc < fc_best))
            {
                fc_best = fc;
                for (i = 1; i <= N_vtx; i++)
                    Best_Color[i] = Color[i];
            }

            if ((f == 0) && (fc == 0))
            {
                System.out.print("finis f == 0 and fc == 0" + "\n");
                return 0;
            }
        }
        System.out.print("Here f = " + f + " fc_best = " + fc_best + " fc = " + fc + "\n");
        if (f_best == 0)
            return fc_best;
        else
            return 9999999;
    }//深度搜索
    private int Compoud_Tabu_Search_BTH(int search_depth){
        int i, j, m, tl, l;
        int num_best;
        int x, y, v;
        int iter;
        int best_delta, delt;
        int v_best_delta, v_delt;
        int old_color, old_color_x, old_color_y;//移动前着色类
        int select;//随机选择方案
        int num;//可大致认为是邻域总数
        copy_ini_solution();
        f_best = f;//f_best为当前的惩罚计数

        f_crs = 0;
        //初始班级课表优劣
        for (i = 1; i <= MNum; i++)
        {
            m = Set_cl[i][1];//是哪一节课
            Class_Score[i] = Cal_Class_Score(m, Color[m]);
            f_crs += Class_Score[i];
        }
        //教师课表优劣
        System.out.print("f_crs=" + f_crs + "\n");
        Sort_Src();
        for (i = 1; i <= Tea_Num; i++)
            Tea_JP_Score[i] = 0;
        for (i = 1; i <= MNum; i++)
        {
            m = Set_cl[i][1];//是哪一节课
            for (j = 1; j <= Node_Tea[m][0]; j++)
            {
                if (Node_Tea[m][j] > 0)
                {
                    Tea_JP_Score[Node_Tea[m][j]] = Cal_JP_Score(m, Color[m], Node_Tea[m][j]);//哪位老师
                }
            }
        }
        f_tea = 0;
        for (i = 1; i <= Tea_Num; i++)
        {
            f_tea = f_tea + Tea_JP_Score[i];
        }
        System.out.print("f_tea1=" + f_tea + "\n");
        set_tea_Src();//计算课程集中程度
        f_tea = 0;
        for (i = 1; i <= Tea_Num; i++)
        {
            f_tea = f_tea + Tea_Score[i];
        }
        System.out.print("f_tea2=" + f_tea + "\n");
        fc = f_crs + f_tea;
        fc_best = fc;
        System.out.print("f=" + f + " fc=" + fc + "\n");
        if ((f == 0) && (fc == 0))
        {
            System.out.print("finis f == 0 and fc == 0 and ftea == 0" + "\n");
            return 1;
        }
        System.out.print("In begin of Compoud_Tabu_Search_BTH f = " + f + " fc = " + fc + "\n");

        Ini_Delta_Crs();//课程集中程度
        Ini_Delta_Ttm();//教师课程集中程度
        Ini_Delta_Tpj();//教案平齐

        //当前最优解为当前解
        for (i = 1; i <= N_vtx; i++)
            Best_Color[i] = Color[i];
        for (iter = 1; iter <= search_depth; iter++)
        {
            num = 0;
            best_delta = 9999999;
            v_best_delta = 9999999;
            num_best = 0;

            for (x = 1; x <= N_vtx; x++)
            {
                l = 0;
                for (i = 1; i <= Node_Tea[x][0]; i++)
                {
                    if ((Node_Tea[x][i] > 0) && (Tea_Score[Node_Tea[x][i]] > 0))
                    {
                        l = 1;
                    }
                }////教师是否>0
                if ((Delta_Matrix[x][Color[x]])>0 || (Class_Score[Par[x]])>0 || l == 1)
                {
                    if (Node_Tea_Sign[x] != -2 && YPK[x] == 0)
                    {
                        for (v = 1; v <= KCL; v++)
                            if ((AVB_Cl[x][v] == 1) && (v != Color[x]) && (TabuTenure[x][v] <= iter))
                            {
                                delt = Delta_Matrix[x][v] - Delta_Matrix[x][Color[x]];
                                //cout << "inner_iter = " << iter << endl;
                                if (delt < best_delta)
                                {
                                    best_x[0] = x;
                                    best_v[0] = v;
                                    best_delta = delt;
                                    v_best_delta = Delta_Crs[x][v] + Delta_Tpj[x][v] + Delta_Ttm[x][v]; //+ 3 * (PCr[v] - PCr[Color[x]]);

                                    num_best = 1;
                                }
                                else if (delt == best_delta)  //if some flips have the same delta,then choose at most 50 and record them   && num_best < 50
                                {
                                    v_delt = Delta_Crs[x][v] + Delta_Tpj[x][v] + Delta_Ttm[x][v];// +3 * (PCr[v] - PCr[Color[x]]);
                                    if (v_delt < v_best_delta)
                                    {
                                        best_x[0] = x;
                                        best_v[0] = v;
                                        best_delta = delt;
                                        v_best_delta = v_delt;
                                        num_best = 1;
                                    }
                                    else if ((v_delt == v_best_delta) && (num_best < 1000))
                                    {
                                        best_x[num_best] = x;
                                        best_v[num_best] = v;
                                        num_best++;
                                    }
                                }
                            }
                    }
                }
            }


            for (x = 1; x <= N_vtx; x++)
            {
                l = 0;
                for (i = 1; i <= Node_Tea[x][0]; i++)
                {
                    if ((Node_Tea[x][i] > 0) && (Tea_Score[Node_Tea[x][i]] > 0))
                    {
                        l = 1;
                    }
                }////教师是否>0
                if ((Delta_Matrix[x][Color[x]])>0 || (Class_Score[Par[x]])>0 || l == 1)
                {
                    for (y = 1; y <= N_vtx; y++)
                        if ((MCls[Par[x]] == MCls[Par[y]]) && (AVB_Cl[x][Color[y]] == 1) && (AVB_Cl[y][Color[x]] == 1) && (Par[x] != Par[y]) && (Color[y] != Color[x]) && (TabuTenure[x][Color[y]] <= iter) && (TabuTenure[y][Color[x]] <= iter))
                        {
                            if ((Node_Tea_Sign[x] != -2) && (Node_Tea_Sign[y] != -2) && YPK[x] == 0 && YPK[y] == 0)
                            {
                                delt = Delta_Matrix[x][Color[y]] - Delta_Matrix[x][Color[x]] + Delta_Matrix[y][Color[x]] - Delta_Matrix[y][Color[y]] - 2;
                                if (delt < best_delta)
                                {
                                    best_x[0] = x;
                                    best_v[0] = -y;
                                    best_delta = delt;
                                    if (Node_Tea_Sign[x] == Node_Tea_Sign[y])//有问题要改
                                        v_best_delta = Delta_Crs[x][Color[y]] + Delta_Crs[y][Color[x]] + Delta_Tpj[x][Color[y]] + Delta_Tpj[y][Color[x]];
                                    else
                                        v_best_delta = Delta_Crs[x][Color[y]] + Delta_Crs[y][Color[x]] + Delta_Tpj[x][Color[y]] + Delta_Tpj[y][Color[x]] + Delta_Ttm[x][Color[y]] + Delta_Ttm[y][Color[x]];
                                    num_best = 1;
                                }
                                else if (delt == best_delta)  //if some flips have the same delta,then choose at most 50 and record them   && num_best < 50
                                {
                                    if (Node_Tea_Sign[x] == Node_Tea_Sign[y])
                                        v_delt = Delta_Crs[x][Color[y]] + Delta_Crs[y][Color[x]] + Delta_Tpj[x][Color[y]] + Delta_Tpj[y][Color[x]];
                                    else
                                        v_delt = Delta_Crs[x][Color[y]] + Delta_Crs[y][Color[x]] + Delta_Tpj[x][Color[y]] + Delta_Tpj[y][Color[x]] + Delta_Ttm[x][Color[y]] + Delta_Ttm[y][Color[x]];
                                    if (v_delt < v_best_delta)
                                    {
                                        best_x[0] = x;
                                        best_v[0] = -y;
                                        best_delta = delt;
                                        v_best_delta = v_delt;
                                        num_best = 1;
                                    }
                                    else if ((v_delt == v_best_delta) && (num_best < 1000))
                                    {
                                        best_x[num_best] = x;
                                        best_v[num_best] = -y;
                                        num_best++;
                                    }
                                }
                            }
                        }
                }
            }

            if (num_best > 0)
                select = rand.nextInt(num_best);   //从备选方案中随机选择一个方案
            else
                return fc_best;

            if (best_v[select] > 0)
            {
                old_color = Color[best_x[select]];  //存储顶点移动之前的着色类
                f += Delta_Matrix[best_x[select]][best_v[select]] - Delta_Matrix[best_x[select]][old_color];
                fc += Delta_Crs[best_x[select]][best_v[select]];
                One_Move_Update_Delta_Matrix(best_x[select], old_color, best_v[select]);//更新增量矩阵
                One_Move_Update_Course(best_x[select], old_color, best_v[select]);
                Color[best_x[select]] = best_v[select];//更新着色类方案
                One_Move_Update_JP(best_x[select]);

                tl = iter % 1500;
                TabuTenure[best_x[select]][Color[best_x[select]]] = tabuh[tl] + iter;
                m = Update_Tea_Src(best_x[select], best_x[select]);
                fc += m - f_tea;
                f_tea = m;

                Update_Delta_Crs(best_x[select]);
                Update_Delta_Ttm(best_x[select]);
                Update_Delta_Tpj(best_x[select]);
            }
            else
            {
                x = best_x[select];
                y = -best_v[select];

                f += Delta_Matrix[x][Color[y]] - Delta_Matrix[x][Color[x]] + Delta_Matrix[y][Color[x]] - Delta_Matrix[y][Color[y]] - 2 * Edge[x][y];
                fc += Delta_Crs[x][Color[y]] + Delta_Crs[y][Color[x]];// + cal_value_swap_tea(x,y);

                old_color_x = Color[x];  //存储顶点移动之前的着色类
                old_color_y = Color[y];
                One_Move_Update_Delta_Matrix(x, old_color_x, old_color_y);//更新增量矩阵
                One_Move_Update_Course(x, old_color_x, old_color_y);
                Color[x] = old_color_y;//更新着色类方案
                One_Move_Update_JP(x);

                tl = iter % 1500;
                TabuTenure[x][Color[x]] = tabuh[tl] + iter;

                One_Move_Update_Delta_Matrix(y, old_color_y, old_color_x);//更新增量矩阵
                One_Move_Update_Course(y, old_color_y, old_color_x);
                Color[y] = old_color_x;//更新着色类方案
                One_Move_Update_JP(y);

                TabuTenure[y][Color[y]] = tabuh[tl] + iter;
                m = Update_Tea_Src(x, y);
                fc += m - f_tea;
                f_tea = m;

                Update_Delta_Crs(x);
                Update_Delta_Ttm(x);
                Update_Delta_Tpj(x);
                Update_Delta_Crs(y);
                Update_Delta_Ttm(y);
                Update_Delta_Tpj(y);
            }

            if (iter % 1000 == 0)
                reset_tabu_tenure();

            if (f < f_best) //f improved ,update f_best and colour
            {
                f_best = f;//更新当前最优惩罚值
                fc_best = fc;
                for (i = 1; i <= N_vtx; i++)
                    Best_Color[i] = Color[i];

            }
            else if ((f <= f_best) && (fc < fc_best))
            {
                fc_best = fc;
                for (i = 1; i <= N_vtx; i++)
                    Best_Color[i] = Color[i];
            }

            if ((f == 0) && (fc == 0))
            {
                System.out.print("finis f == 0 and fc == 0" + "\n");
                return 0;
            }
        }
        System.out.print("f = " + f + " fc_best = " + fc_best + " fc = " + fc + "\n");
        if (f_best == 0)
            return fc_best;
        else
            return 9999999;
    }//第二次深度搜索
    private int Cal_Class_Score(int ndi, int knew){
        int pnd = Par[ndi];//属于哪一门课
        int kold = Color[ndi];
        int Class = MCls[pnd];
        int len;
        int f1 = 0;
        int maxk = Weak_Day / 2;//3天
        if (maxk < 3)
            maxk = 3;
        int i, j, k, l, len_lt, max, min;
        int x, y;
        int crsMr = 0;
        int crsAf = 0;
        int c_Num = Node_Course[ndi][0];//这节课的课程属性
        for (x = 1; x <= c_Num; x++)
        {
            //可以进行改进
            len = Set_Crs[MCls[pnd]][Node_Course[ndi][x]][0];//同一个班的同一门课程有哪些课
            for (i = 1; i <= Set_Crs[ MCls[pnd] ][ Node_Course[ndi][x] ][0]; i++)//这些课在哪个时间点
            {
                if (Set_Crs[MCls[pnd]][Node_Course[ndi][x]][i] != ndi)
                    set[i] = Color[ Set_Crs[ MCls[pnd] ][ Node_Course[ndi][x] ][i] ];
                else
                    set[i] = knew;
            }
            int flag = 0;
            int dayZ = 0;
            int  m_c = Weak_Day*Day_Crs;//35天=KCL
            for (i = 1; i <= Weak_Day; i++)
            {
                daycr[i] = 0;//课时
                daylt[i] = 0;//连堂课
            }

            for (i = 1; i <= len; i++)
                daycr[PDay[set[i]]]++;//每天上几节

            for (i = 1; i <= len; i++)
            {
                if(PCr[set[i]] >= Day_Crs_Mr)
                {
                    crsMr++;//上午的课时数增加
                }
                else
                {
                    crsAf++;//下午的课时数增加
                }
            }

            int gcls = MCls[pnd];
            int gcr = MCrs[pnd];

            if (Class_Crs_Num_L[gcls][gcr] > 0)//如果存在连堂课
            {
                for (i = 1; i <= m_c; i++)
                    All_cl[i] = 0;
                for (i = 1; i <= len; i++)
                    All_cl[set[i]] = 1;
                len_lt = 0;
                for (i = 1; i < m_c; i++)
                    if ((All_cl[i] == 1) && (All_cl[i + 1] == 1) && (PCr[i] != Day_Crs) && (PCr[i] != Day_Crs_Mr))
                    {
                        len_lt++;//连堂课数量+1
                        dayZ = PDay[i];//属于哪一天
                        daylt[dayZ]++;//这一天的连堂课数量+1
                        i++;
                    }
                if (len_lt < Class_Crs_Num_L[gcls][gcr])
                    f1 += 50 * (Class_Crs_Num_L[gcls][gcr] - len_lt);

                if (len_lt > Class_Crs_Num_L[gcls][gcr])//不能多也不能少
                    f1 += 50 * (len_lt - Class_Crs_Num_L[gcls][gcr]);
                max = -9999;
                min = 9999;
                for (i = 1; i <= Weak_Day; i++)
                {
                    if (daycr[i] > max)//一天最多上几节
                        max = daycr[i];
                    if (daycr[i] < min)//一天最少上几节
                        min = daycr[i];
                }
                if (max - min > 2)
                    f1 += 60;

                for (i = 1; i <= Weak_Day; i++)
                    if (daylt[i] == 0)//如果这一天没有连堂课
                    {
                        if (daycr[i] > Max_Day[pnd])//判断有没有超多一天最大的节数
                            f1 += 10;
                        if (daycr[i] < Min_Day[pnd])
                            f1 += 10;
                    }

                if (Ctwo == 1)//如果需要优先排课
                    if (Cct[MCrs[pnd]] == 1)
                    {
                        for (i = 1; i <= len; i++)
                            if (PCr[set[i]] > C_Time)
                                f1 += 3;
                    }

                if (Crs_Attri[pnd] == 1)
                {
                    l = 0;
                    for (i = 1; i <= len; i++)
                        if (PCr[set[i]] == 1)
                            l++;
                    if (l >= maxk)//有一半排在第一节
                        f1 += 4;
                }

                //课程上下午分布
                if(crsMr > Max_Mr[pnd] || crsAf >Max_Af[pnd])
                {
                    f1 +=2;

                }


            }
            else//没有连堂课
            {
                if (Ctwo == 1)
                    if (Cct[MCrs[pnd]] == 1)
                    {
                        for (i = 1; i <= len; i++)
                            if (PCr[set[i]] > C_Time)
                                f1 += 3;
                    }

                for (j = 1; j <= Weak_Day; j++)
                {
                    if (daycr[j] > Max_Day[pnd])
                        f1 += 10;
                    if (daycr[j] < Min_Day[pnd])
                        f1 += 10;
                }

                if (Crs_Attri[pnd] == 3)//3天不能连上
                {
                    for (j = 1; j <= Weak_Day - 2; j++)
                        if ((daycr[j] > 0) && (daycr[j + 1] > 0) && (daycr[j + 2] > 0))
                        {
                            f1 += 2;
                        }
                }
                else if (Crs_Attri[pnd] == 2)//两天不能连上
                {
                    for (j = 1; j <= Weak_Day - 1; j++)
                        if ((daycr[j] > 0) && (daycr[j + 1] > 0))
                        {
                            f1 += 2;
                        }
                }

                if (Crs_Attri[pnd] == 1)//对于一周上5节课的课程
                {
                    l = 0;
                    for (i = 1; i <= len; i++)
                        if (PCr[set[i]] == 1)//连续半周排第一节
                            l++;
                    if (l >= maxk)
                        f1 += 4;
                }

                //课程上下午分布
                if(crsMr > Max_Mr[pnd] || crsAf >Max_Af[pnd])
                {
                    f1 +=2;

                }
            }
        }
        return f1;
    }
    private int Cal_JP_Score(int ndi, int knew, int t){
        int crs = Par[ndi];//是哪一门课
        int x,y,z;
        int i, j, k, f1, l, m, n, max_pre, min_l, min_c, max_c;
        f1 = 0;
        int[] Tea_set=new int[30];
        int[] maxc=new int[10 + 1];
        int[] minc=new int[10 + 1];
        int[] maxl=new int[10 + 1];
        int[] minl=new int[10 + 1];
        int len;
        int len_l;
        for (i = 1; i <= Set_cl[crs][0]; i++) //记录初始的状态
        {
            set[i] = Crs_srt_cl[crs][i];//这一门课的所有课
        }

        for (i = 1; i <= Set_cl[crs][0]; i++)
            if (Crs_srt_cl[crs][i] == Color[ndi])
            {
                Crs_srt_cl[crs][i] = knew;
                break;
            }//每个都着不同的色了已经
        bubble_sort(Crs_srt_cl[crs], Set_cl[crs][0]);//这门课重新排序

        for (x = 1; x <= Tea_CP_Sign[t][0]; x++)//一个老师的所有课
        {
            //循环次数太多
            len = 0;
            for (y = 1; y <= Tea_CP[t][0]; y++)
            {
                if (Tea_CP_Sign[t][y] == x)//属于需要比较的
                {
                    len++;
                    Tea_set[len] = Tea_CP[t][y];
                }
            }
            Tea_set[0] = len;//有多少门课
            int pnd = Tea_set[1];//新值

            if (Class_Crs_Num_L[MCls[pnd]][MCrs[pnd]] > 0)
            {
                len_l = 0;
                //第一种判定方式
                for (j = 1; j <= 10; j++)
                {
                    maxc[j] = -1;
                    minc[j] = 99999;
                }
                for (j = 1; j <= len; j++)
                {
                    k = Tea_set[j]; //老师教的这门课，有几门课需要进行比较
                    l = 0;
                    for (i = 2; i <= Set_cl[pnd][0]; i++)
                    {
                        if (((Crs_srt_cl[k][i] - Crs_srt_cl[k][i - 1]) == 1) && (PDay[Crs_srt_cl[k][i]] == PDay[Crs_srt_cl[k][i - 1]]))
                        {
                            l++;//每一次进入计数
                            if (Crs_srt_cl[k][i - 1] < minc[l])//第l次连课的第一节在什么时候上
                                minc[l] = Crs_srt_cl[k][i-1];
                            if (Crs_srt_cl[k][i] > maxc[l])//第l次连课的第二节在什么时候上
                                maxc[l] = Crs_srt_cl[k][i];
                            //break;
                        }
                    }
                    if (len_l < l)
                    {
                        len_l = l;//有几次连堂课
                    }
                }

                int[] a=new int[30];
                l = 0;
                for (j = 1; j <= len; j++)
                {
                    k = Tea_set[j];
                    for (i = 1; i <= Set_cl[pnd][0]; i++)
                        a[i] = 0;
                    for (i = 2; i <= Set_cl[pnd][0]; i++)
                    {
                        if (((Crs_srt_cl[k][i] - Crs_srt_cl[k][i - 1]) == 1) && (PDay[Crs_srt_cl[k][i]] == PDay[Crs_srt_cl[k][i - 1]]))
                        {
                            a[i] = 1;//找出所有的连堂课
                            a[i - 1] = 1;
                        }
                    }
                    for (i = 1; i <= len_l; i++)//对于第一节课
                    {
                        for (m = 1; m <= Set_cl[pnd][0]; m++)
                        {
                            if ((a[m] == 0) && (Crs_srt_cl[k][m] < maxc[i]) && (Crs_srt_cl[k][m] > minc[i])) //非连堂课去进行判定//不该是这样去检验
                                l++;
                        }
                    }
                }
                if (l > 0)
                    f1 = f1+3;
                else
                    f1 = f1+0;


                //第二种判定方式
                for (j = 1; j <= 10; j++)
                {
                    maxl[j] = -1;
                    minl[j] = 99999;
                }
                for (j = 1; j <= len; j++)
                {
                    k = Tea_set[j];
                    for (i = 1; i <= Set_cl[pnd][0]; i++)
                        a[i] = 0;
                    for (i = 2; i <= Set_cl[pnd][0]; i++)
                    {
                        if (((Crs_srt_cl[k][i] - Crs_srt_cl[k][i - 1]) == 1) && (PDay[Crs_srt_cl[k][i]] == PDay[Crs_srt_cl[k][i - 1]]))
                        {
                            a[i] = 1;
                            a[i - 1] = 1;
                        }
                    }
                    l = 0;
                    for (i = 1; i <= len_l; i++)
                    {
                        for (m = 1; m <= Set_cl[pnd][0]; m++)
                        {
                            if ((a[m] == 0) && (Crs_srt_cl[k][m] < maxc[i])) //最后一节连堂课之前有几节课，一定要一样
                                l++;
                        }
                        if (l > maxl[i])
                            maxl[i] = l;
                        if (l < minl[i])
                            minl[i] = l;
                    }
                }
                for (i = 1; i <= len_l; i++)
                {
                    f1 = f1 + 2 * (maxl[i] - minl[i]);  //另一种判定方式，不同课连堂课之前上的课的数量
                }
                //教案平齐（没连堂的教案平齐和连堂教案平齐）
                if (Class_Crs_Num_L[MCls[pnd]][MCrs[pnd]] > 1)//多次连堂课
                {
                    min_l = 9999;
                    for (j = 1; j <= len; j++)
                    {
                        k = Tea_set[j];
                        for (i = 1; i <= Set_cl[pnd][0]; i++)
                            a[i] = 0;
                        for (i = 2; i <= Set_cl[pnd][0]; i++)
                        {
                            if (((Crs_srt_cl[k][i] - Crs_srt_cl[k][i - 1]) == 1) && (PDay[Crs_srt_cl[k][i]] == PDay[Crs_srt_cl[k][i - 1]]))//连堂课
                            {
                                a[i] = 1;
                                a[i - 1] = 1;
                            }
                        }
                        l = 0;
                        for (i = 1; i <= Set_cl[pnd][0]; i++)
                            if ((a[i] == 1))
                            {
                                l++;//l肯定是双数
                                JP_KC[j][l] = Crs_srt_cl[k][i];//记录连堂课
                            }
                        if (l < min_l)
                            min_l = l/2;//最少的连堂的有几节课/2
                    }
                    for (i = 1; i <= min_l-1; i++)//连堂课的节数，从第二节开始
                    {
                        max_c = -1;
                        min_c = 99999;
                        for (j = 1; j <= len; j++)
                        {
                            if (JP_KC[j][i*2+1] < min_c)
                                min_c = JP_KC[j][i*2+1];
                            if (JP_KC[j][i*2] > max_c)
                                max_c = JP_KC[j][i*2];//最早开始和最晚开始的两节课
                        }
                        if (min_c < max_c)//最早开始的要比前一节最晚开始的早，不对
                            f1 += 2;
                    }
                }
                //进行非连堂课的教案平齐
                min_l = 9999;
                for (j = 1; j <= len; j++)
                {
                    k = Tea_set[j];
                    for (i = 1; i <= Set_cl[pnd][0]; i++)
                        a[i] = 0;
                    for (i = 2; i <= Set_cl[pnd][0]; i++)
                    {
                        if (((Crs_srt_cl[k][i] - Crs_srt_cl[k][i - 1]) == 1) && (PDay[Crs_srt_cl[k][i]] == PDay[Crs_srt_cl[k][i - 1]]))//非连堂课
                        {
                            a[i] = 1;
                            a[i - 1] = 1;
                        }
                    }
                    l = 0;
                    for (i = 1; i <= Set_cl[pnd][0]; i++)
                        if ((a[i] == 0))
                        {
                            l++;
                            JP_KC[j][l] = Crs_srt_cl[k][i];//没有连课的这几节
                        }
                    if (l < min_l)
                        min_l = l;//没连堂的有几节课
                }
                max_pre = -1;
                for (j = 1; j <= len; j++)
                {
                    if (JP_KC[j][1] > max_pre)
                        max_pre = JP_KC[j][1];//最晚开始的最早的一节课
                }

                for (i = 2; i <= min_l; i++)//连堂课的节数，从第二节开始
                {
                    max_c = -1;
                    min_c = 99999;
                    for (j = 1; j <= len; j++)
                    {
                        if (JP_KC[j][i] < min_c)
                            min_c = JP_KC[j][i];
                        if (JP_KC[j][i] > max_c)
                            max_c = JP_KC[j][i];//最早开始和最晚开始的两节课
                    }
                    if (min_c < max_pre || PDay[min_c] == PDay[max_pre])//最早开始的要比前一节最晚开始的早，不对
                        f1 += 2;
                    max_pre = max_c;//转换
                }
            }
            else
            {
                //如果不触发连堂课，直接判断教案平齐
                max_pre = -1;
                for (j = 1; j <= len; j++)
                {
                    k = Tea_set[j];
                    if (Crs_srt_cl[k][1] > max_pre)
                        max_pre = Crs_srt_cl[k][1];
                }
                for (i = 2; i <= Set_cl[pnd][0]; i++)
                {
                    max_c = -1;
                    min_c = 99999;
                    for (j = 1; j <= len; j++)
                    {
                        k = Tea_set[j];
                        if (Crs_srt_cl[k][i] < min_c)
                            min_c = Crs_srt_cl[k][i];
                        if (Crs_srt_cl[k][i] > max_c)
                            max_c = Crs_srt_cl[k][i];
                    }
                    if (min_c < max_pre || PDay[min_c] == PDay[max_pre])
                        f1 += 3;
                    max_pre = max_c;
                }
            }

        }
        //算完之后回归原位
        for (i = 1; i <= Set_cl[crs][0]; i++)
        {
            Crs_srt_cl[crs][i] = set[i];//这个pnd不是原来的pnd了
        }
        return f1;

    }
    private int Cal_TScr(){
        int i, j, k, maxk, mink, mr, fn;
        int ft = 0;
        int n_mor = 0;
        int n_af = 0;
        int max_mor=0;
        int min_mor = -1;
        int max_af=0;
        int min_af = -1;
        for (i = 1; i <= Day_Crs_Mr; i++)
            if (TScr[i] != 0)
            {
                n_mor++;
                if (min_mor == -1)
                    min_mor = i;
                max_mor = i;
            }

        for (i = Day_Crs_Mr + 1; i <= Day_Crs; i++)
            if (TScr[i] != 0)
            {
                n_af++;
                if (min_af == -1)
                    min_af = i;
                max_af = i;
            }

        if ((n_mor > 0) && (n_af > 0))
            return (Day_Crs_Mr + max_mor - min_mor + 1 - n_mor + max_af - min_af + 1 - n_af);
        if (n_mor > 0)
            return (max_mor - min_mor + 1 - n_mor);
        if (n_af > 0)
            return (max_af - min_af + 1 - n_af);
        return 0;
    }
    private int Cal_value_one_move_tea(int ndi, int knew,int th){
        int pnd = Par[ndi];
        int kold = Color[ndi];
        int i, j, k, l, m, l1, l2;
        i = MCls[pnd];//哪个班
        j = MCrs[pnd];//哪节课
        if (Tct[th] == 0)//不需要集中排课
            return 0;

        int day_old, crs_old, day_new, crs_new;
        day_old = PDay[kold];//原来在哪天
        day_new = PDay[knew];//新的在哪天
        crs_old = PCr[kold];//原来在第几节
        crs_new = PCr[knew];//新的在第几节
        if (day_old == day_new)//如果是在同一天
        {
            for (i = 1; i <= Day_Crs; i++)
            {
                TScr[i] = Tea_Crs[th][day_old][i];
            }
            TScr[crs_old]--;
            TScr[crs_new]++;
            l = Cal_TScr() - Tea_Scr[th][day_old];
            return l;
        }
        else
        {
            for (i = 1; i <= Day_Crs; i++)
            {
                TScr[i] = Tea_Crs[th][day_old][i];
            }
            TScr[crs_old]--;
            l1 = Cal_TScr() - Tea_Scr[th][day_old];

            for (i = 1; i <= Day_Crs; i++)
            {
                TScr[i] = Tea_Crs[th][day_new][i];
            }
            TScr[crs_new]++;
            l2 = Cal_TScr() - Tea_Scr[th][day_new];
            return l1 + l2;
        }
    }
    private void greed_ini_solution() throws MyException {
        int i, j, k, l,v;
        int x,y;
        int num_best;
        int pick,day,crs;
        int old_color;
        int iter;
        int best_delta, delt, v_delt, v_best_delta;
        int select;

        setcolor();
        Clear_Delta_Matrix();
        Build_Delta_Matrix();//初始化

        l = 0;
        for (i = 0; i <= Weak_Day; i++)
            Day_Set[i] = 0;

        for (x = 1; x <= N_vtx; x++)
            if ((Color[x] == 0) && (Node_Tea_Sign[x] == -2))
            {
                while (1>0)
                {
                    pick = 0;
                    k = rand.nextInt(KCL) + 1;
                    for (y = 1; y <= Synthesis_Node_Num; y++)
                    {
                        if (k == Color[Synthesis_Node[y]])
                        {
                            pick = 1;
                        }
                    }
                    if (pick == 1)
                    {
                        continue;
                    }
                    day = PDay[k];//在哪一天
                    crs = PCr[k];//哪一节课
                    if ((AVB_Cl[x][k] == 1) && (Day_Set[day] < Max_Day[Par[x]]) && (crs >= 2))
                    {
                        old_color = Color[x];  //存储顶点移动之前的着色类
                        One_Move_Update_Delta_Matrix(x, old_color, k);//更新增量矩阵
                        Color[x] = k;
                        Day_Set[day]++;
                        break;
                    }
                }
            }
        for (i = 0; i <= Weak_Day; i++)
            Day_Set[i] = 0;
        //*********************************
        for (x = 1; x <= N_vtx; x++)
            if ((Color[x] == 0) && (Node_Tea_Sign[x] == -20))
            {
                while (1>0)
                {
                    pick = 0;
                    k = rand.nextInt(KCL) + 1;
                    for (y = 1; y <= Synthesis_Node_Num; y++)
                    {
                        if (k == Color[Synthesis_Node[y]])
                        {
                            pick = 1;
                        }
                    }
                    if (pick == 1)
                    {
                        continue;
                    }

                    day = PDay[k];
                    crs = PCr[k];
                    if ((AVB_Cl[x][k] == 1) && (Day_Set[day] < Max_Day[Par[x]]) && (crs >= 2))
                    {
                        old_color = Color[x];  //存储顶点移动之前的着色类
                        One_Move_Update_Delta_Matrix(x, old_color, k);//更新增量矩阵
                        Color[x] = k;
                        Day_Set[day]++;
                        break;
                    }
                }
            }


        for (x = 1; x <= N_vtx; x++)
        {
            if (Color[x] > 0)
            {
                l++;
            }
        }
        for (iter = l + 1; iter <= N_vtx; iter++)
        {
            best_delta = 9999999;
            v_best_delta = 9999999;
            num_best = 0;
            for (x = 1; x <= N_vtx; x++)
                if (Color[x] == 0)
                {
                    for (v = 1; v <= KCL; v++)
                        if ((AVB_Cl[x][v] == 1))
                        {
                            delt = Delta_Matrix[x][v];
                            if (delt < best_delta)
                            {
                                best_x[0] = x;
                                best_v[0] = v;
                                best_delta = delt;
                                v_best_delta = Delta_Matrix[x][0];
                                num_best = 1;
                            }
                            else if (delt == best_delta)  //if some flips have the same delta,then choose at most 50 and record them   && num_best < 50
                            {
                                v_delt = Delta_Matrix[x][0];
                                if (v_delt > v_best_delta)
                                {
                                    best_x[0] = x;
                                    best_v[0] = v;
                                    best_delta = delt;
                                    v_best_delta = v_delt;
                                    num_best = 1;
                                }
                                else if ((v_delt == v_best_delta) && (num_best < 1000))
                                {
                                    best_x[num_best] = x;
                                    best_v[num_best] = v;
                                    num_best++;
                                }
                            }
                        }
                }
            if (num_best == 0)
            {
                System.out.print("对课程的时间约束太多，无法生成合法解"+"\n");
                throw new MyException("对课程的时间约束太多，无法生成合法解");
            }
            select = rand.nextInt( num_best);
            old_color = Color[best_x[select]];  //存储顶点移动之前的着色类
            One_Move_Update_Delta_Matrix(best_x[select], old_color, best_v[select]);//更新增量矩阵
            Color[best_x[select]] = best_v[select];//更新着色类方案
        }
        f = 0;
        for (i = 1; i <= N_vtx; i++)
            for (j = 1; j < i; j++)
                if (Edge[i][j] != 0)
                {
                    if (Color[i] == Color[j])
                        f += 1;
                }

        System.out.print("f = " + f + "\n");

    }
    private void copy_ini_solution(){
        int i, j;
        for (i = 1; i <= N_vtx; i++)
        {
            Color[i] = Best_Color[i];
        }
        Clear_Delta_Matrix();
        Build_Delta_Matrix();
    }
    private void setcolor(){
        int i;
        for (i = 1; i <= N_vtx; i++)
        {
            Color[i] = Initial_Color[i];
        }
    }
    private void Clear_Delta_Matrix(){
        int x, v;
        f = 0;
        f_crs = 0;
        for (x = 0; x <= N_vtx; x++)
            for (v = 0; v <= KCL; v++)
                Delta_Matrix[x][v] = 0; //Delta_Matrix[x][v] means the change on function when the colour of x flip to colour v

        for (x = 0; x <= N_vtx; x++)
            for (v = 0; v <= KCL; v++)
                TabuTenure[x][v] = 0; //TabuTenure[x][v]means in the following generation the colour of x can not be flip to clour v
    }
    private void Build_Delta_Matrix(){
        int i, j;
        f = 0;
        for (i = 1; i <= N_vtx; i++)
            for (j = 1; j < i; j++)
                if (Edge[i][j] != 0)
                {
                    Delta_Matrix[i][Color[j]] += 1;
                    Delta_Matrix[j][Color[i]] += 1;
                    if (Color[i] == Color[j])
                        f += 1;
                }
    }



    private void One_Move_Update_Delta_Matrix(int tt, int v0, int v1) {
        int i, len, x;
        len = A_Matrix[tt][0];
        for (i = 1; i <= len; i++)
        {
            x = A_Matrix[tt][i];
            Delta_Matrix[x][v0] -= Edge[x][tt];
            Delta_Matrix[x][v1] += Edge[x][tt];
        }
    }
    private void One_Move_Update_Course(int tt, int v0, int v1){
        int pnd = Par[tt];
        Class_Score[pnd] = Cal_Class_Score(tt, v1);
    }
    private void One_Move_Update_JP(int tt){
        int i, j, k, l, t;
        int pnd = Par[tt];//这节课属于哪门课
        for (i = 1; i <= Set_cl[pnd][0]; i++)
            Crs_srt_cl[pnd][i] = Color[Set_cl[pnd][i]];//颜色已经改变
        bubble_sort(Crs_srt_cl[pnd], Set_cl[pnd][0]);

        for (i = 1; i <= Node_Tea[tt][0]; i++)
        {
            t = Node_Tea[tt][i];
            if (t > 0)
                Tea_JP_Score[t] = Cal_JP_Score(tt, Color[tt],t);
        }
    }
    private void Sort_Src(){
        int i, j, c, l, m;
        for (i = 1; i <= MNum; i++)
        {
            for (j = 1; j <= Set_cl[i][0]; j++)
            {
                m = Set_cl[i][j];
                c = Color[m];
                Crs_srt_cl[i][j] = c;//哪节课在哪个时间段上
            }
            bubble_sort(Crs_srt_cl[i], Set_cl[i][0]);
            Crs_srt_cl[i][0] = Set_cl[i][0];
        }
    }
    private void bubble_sort(int pset[], int len){
        int i, j, temp;
        for (i = 1; i < len; i++)
            for (j = 1; j < len + 1 - i; j++)
                if (pset[j] > pset[j + 1])
                {
                    temp = pset[j];
                    pset[j] = pset[j + 1];
                    pset[j + 1] = temp;
                }
    }
    private void Ini_Delta_Crs(){
        int i, j, l;
        for (i = 1; i <= N_vtx; i++)
        {
            l = Cal_Class_Score(i, Color[i]);
            for (j = 1; j <= KCL; j++)
                Delta_Crs[i][j] = Cal_Class_Score(i, j) - l;
        }

    }
    private void Ini_Delta_Tpj(){
        int i, j, k;
        int gl;
        for (i = 1; i <= N_vtx; i++)
        {
            for (j = 1; j <= KCL; j++)
            {
                Delta_Ttm[i][j] = 0;
            }
        }
        for (i = 1; i <= N_vtx; i++)
        {
            for (j = 1; j <= KCL; j++)
            {
                gl = 0;
                for (k = 1; k <= Node_Tea[i][0]; k++)
                {
                    if (Node_Tea[i][k] <= 0)//
                    {
                        gl += 0;
                    }
                    else
                    {
                        gl += Cal_value_one_move_tea(i, j, Node_Tea[i][k]);//告知是哪个教师，能传进来的必大于0
                    }
                }
                Delta_Ttm[i][j] = gl;
            }
        }
    }
    private void Ini_Delta_Ttm(){
        int i, j, k, l;
        int gl;
        int gll;
        for (i = 1; i <= N_vtx; i++)
        {
            for (j = 1; j <= KCL; j++)
            {
                Delta_Tpj[i][j] = 0;
            }
        }

        for (i = 1; i <= N_vtx; i++)
        {
            gll = 0;
            if (Node_Tea_Sign[i] <= 0)
            {
                for (k = 1; k <= KCL; k++)
                    Delta_Tpj[i][k] = 0;
                continue;
            }
            else
            {
                for (j = 1; j <= Node_Tea[i][0]; j++)
                {
                    gll = 1;
                    l = Cal_JP_Score(i, Color[i], Node_Tea[i][j]);
                    for (k = 1; k <= KCL; k++)
                    {
                        dgl[k] += Cal_JP_Score(i, k, Node_Tea[i][j]) - l;
                    }
                }
            }
            if (gll == 1)
            {
                for (k = 1; k <= KCL; k++)
                {
                    Delta_Tpj[i][k] = dgl[k];
                    dgl[k] = 0;
                }
            }
        }

    }

    private void Update_Delta_Crs(int ndi){
        int i, j, k, l, delta;

        for (k = 1; k <= Set_cl[Par[ndi]][0]; k++)
        {
            i = Set_cl[Par[ndi]][k];
            l = Cal_Class_Score(i, Color[i]);
            for (j = 1; j <= KCL; j++)
                Delta_Crs[i][j] = Cal_Class_Score(i, j) - l;
        }

    }
    private void Update_Delta_Tpj(int ndi){
        int i, j, k, l,m;
        int x, y;
        int gl;
        int gll;

        //cout << "Tea" << endl;
        //存在问题
        if (Node_Tea_Sign[ndi] <= 0)
        {
            return;
        }

        for (i = 1; i <= N_vtx; i++)
        {
            gll = 0;
            for (x = 1; x <= Node_Tea[i][0]; x++)
            {
                for (y = 1; y <= Node_Tea[ndi][0]; y++)
                {
                    if (Node_Tea[i][x] == Node_Tea[ndi][y])//只要是教师相同的点,就会受到影响
                    {
                        gll = 1;
                        l = Cal_JP_Score(i, Color[i], Node_Tea[i][x]);
                        for (m = 1; m <= KCL; m++)
                        {
                            dgl[m] += (Cal_JP_Score(i, m, Node_Tea[i][x]) - l);
                        }
                    }
                }

            }
            if (gll == 1)
            {
                for (j = 1; j <= KCL; j++)
                {
                    Delta_Tpj[i][j] = dgl[j];
                    dgl[j] = 0;
                }

            }
        }


    }
    private void Update_Delta_Ttm(int ndi){
        int i, j, x, y;
        int gl;
        int gll;
        if (Node_Tea_Sign[ndi] <= 0)
        {
            return;
        }
        for (i = 1; i <= N_vtx; i++)
        {

            for (j = 1; j <= KCL; j++)
            {
                gll = 0;//判断用
                gl = 0;
                for (x = 1; x <= Node_Tea[i][0]; x++)
                {
                    for (y = 1; y <= Node_Tea[ndi][0]; y++)
                    {
                        if (Node_Tea[i][x] == Node_Tea[ndi][y])
                        {
                            gll = 1;
                            gl += Cal_value_one_move_tea(i, j, Node_Tea[i][x]);//告知是哪个教师，能传进来的必大于0
                        }

                    }
                }
                if (gll == 1)
                {
                    Delta_Ttm[i][j] = gl;
                }
            }
        }

    }
    private int Update_Tea_Src(int x, int y){
        int i, j, k, l;
        set_tea_Src(x, y);
        l = 0;
        for (i = 1; i <= Tea_Num; i++)
        {
            l += Tea_Score[i];
        }
        return l;
    }
    private void set_tea_Src(){
        int i, j, k, l, m, n, c, s, t, th, ii;
        for (i = 0; i <= Tea_Num; i++)
        {
            for (j = 0; j <= Weak_Day; j++)
            {
                for (k = 0; k <= Day_Crs; k++)
                    Tea_Crs[i][j][k] = 0;
            }
        }

        for (i = 1; i <= N_vtx; i++)
        {
            for (j = 1; j <= Node_Tea[i][0]; j++)//多少教师
            {
                c = Color[i];
                th = Node_Tea[i][j];
                if(th>0)
                    Tea_Crs[th][PDay[c]][PCr[c]]++;
            }
        }

        for (i = 1; i <= Tea_Num; i++)
        {
            Tea_Score[i] = 0;
            for (j = 1; j <= Weak_Day; j++)
            {
                if (Tct[i] == 1)
                {
                    for (k = 1; k <= Day_Crs; k++)
                        TScr[k] = Tea_Crs[i][j][k];//哪几节上的
                    Tea_Scr[i][j] = Cal_TScr();
                }
                else
                    Tea_Scr[i][j] = 0;
                Tea_Score[i] += Tea_Scr[i][j];
            }
            Tea_Score[i] = Tea_Score[i] + Tea_JP_Score[i];
        }
    }
    private void set_tea_Src(int x, int y){
        int i, j, k, l, m, n, c, s, t, th, ii;
        for (i = 0; i <= Tea_Num; i++)
        {
            for (j = 0; j <= Weak_Day; j++)
            {
                for (k = 0; k <= Day_Crs; k++)
                    Tea_Crs[i][j][k] = 0;
            }
        }

        for (i = 1; i <= N_vtx; i++)
        {
            for (j = 1; j <= Node_Tea[i][0]; j++)//多少教师
            {
                c = Color[i];
                th = Node_Tea[i][j];
                if (th>0)
                    Tea_Crs[th][PDay[c]][PCr[c]]++;
            }
        }

        if (Node_Tea_Sign[x] > 0)
        {
            for (i = 1; i <= Node_Tea[x][0]; i++)
            {
                for (j = 1; j <= Weak_Day; j++)
                {
                    if (Tct[Node_Tea[x][i]] == 1)
                    {
                        for (k = 1; k <= Day_Crs; k++)
                            TScr[k] = Tea_Crs[Node_Tea[x][i]][j][k];
                        Tea_Scr[Node_Tea[x][i]][j] = Cal_TScr();
                        //T_f[ TPP[ x ] ] += Tea_Scr[ TPP[ x ] ][ j ];
                    }
                    else
                        Tea_Scr[Node_Tea[x][i]][j] = 0;
                }
            }

        }

        if ((Node_Tea_Sign[x] != Node_Tea_Sign[y]) && (Node_Tea_Sign[y] > 0))
        {
            for (i = 1; i <= Node_Tea[y][0]; i++)
            {
                for (j = 1; j <= Weak_Day; j++)
                {
                    if (Tct[Node_Tea[y][i]] == 1)
                    {
                        for (k = 1; k <= Day_Crs; k++)
                            TScr[k] = Tea_Crs[Node_Tea[y][i]][j][k];
                        Tea_Scr[Node_Tea[y][i]][j] = Cal_TScr();
                    }
                    else
                        Tea_Scr[Node_Tea[y][i]][j] = 0;
                }
            }
        }

        for (i = 1; i <= Tea_Num; i++)
        {
            Tea_Score[i] = 0;
            for (j = 1; j <= Weak_Day; j++)
            {
                Tea_Score[i] += Tea_Scr[i][j];
            }
            Tea_Score[i] = Tea_Score[i] + Tea_JP_Score[i];
        }
    }
    private void reset_tabu_tenure(){
        int x, v;
        for (x = 0; x <= N_vtx; x++)
            for (v = 0; v <= KCL; v++)
                TabuTenure[x][v] = 0;
    }
    private void get_reslut(){
        int i, j, k, l, m, n, c, s, t, th, ii, bn, x, y=0;
        int M = 0;
        for (i = 0; i <= Class_Num; i++)
        {
            for (j = 0; j <= Weak_Day; j++)
            {
                for (k = 0; k <= Day_Crs; k++)
                    Class_Crs[i][j][k] = 0;
            }
        }
        for (i = 0; i <= Tea_Num; i++)
        {
            for (j = 0; j <= Weak_Day; j++)
            {
                for (k = 0; k <= Day_Crs; k++)
                    Tea_Crs[i][j][k] = 0;
            }
        }


        System.out.print("Start Class_Crs get_result" + "\n");

        l = 1;
        System.out.print("H1"+"\n");
        for (i = 1; i <= Class_Num; i++)
        {
            for (j = 1; j <= Crs_Num; j++)
                if (Class_Crs_Num[i][j]>0)
                {
                    if (Class_Crs_HEBAN[i][j] >= 100000)//触发合班课
                    {
                        for (t = 1; t <= Class_Crs_Num[i][j]; t++)
                        {
                            m = Set_cl[l][t];
                            c = Color[m];
                            th = Class_Tea[i][j];
                            bn = Class_Crs_HEBAN[i][j] - 100000;//合班课不允许有两个教师
                            for (k = 1; k <= HBK[bn][0]; k++)
                            {
                                Class_Crs[HBK[bn][k]][PDay[c]][PCr[c]] = j;
                            }
                            if (th>0)
                            {
                                for (k = 1; k <= HBK[bn][0]; k++)
                                {
                                    M = (int) Math.pow(100, k);
                                    Tea_Crs[th][PDay[c]][PCr[c]] = Tea_Crs[th][PDay[c]][PCr[c]] + HBK[bn][k]*M;
                                }
                            }
                            if (th == -2)
                            {
                                for (ii = 1; ii <= XK_Tea_Set[0]; ii++)
                                {
                                    m = XK_Tea_Set[ii];
                                    Tea_Crs[m][PDay[c]][PCr[c]] = -3;
                                }
                            }
                        }
                    }
                    else//正常
                    {
                        for (t = 1; t <= Class_Crs_Num[i][j]; t++)
                        {
                            m = Set_cl[l][t];
                            c = Color[m];
                            for (x = 1; x <= Node_Tea[m][0]; x++)
                            {
                                th = Node_Tea[m][x];
                                if (th>0)
                                    Tea_Crs[th][PDay[c]][PCr[c]] = i;
                                if (th == -2)
                                {
                                    for (ii = 1; ii <= XK_Tea_Set[0]; ii++)
                                    {
                                        m = XK_Tea_Set[ii];
                                        Tea_Crs[m][PDay[c]][PCr[c]] = -3;
                                    }
                                }
                            }
                            Class_Crs[i][PDay[c]][PCr[c]] = j;
                        }
                    }
                    l++;
                }
        }
        System.out.print("H2"+"\n");

        for (i = 0; i <Stu_Num; i++)
        {
            for (j = 0; j <= Weak_Day; j++)
            {
                for (k = 0; k <= Day_Crs; k++)
                {
                    Stu_Crs[i][j][k] = Class_Crs[Stu_Class[i]][j][k];
                }

            }
        }

        /*
        System.out.print("Class" + "\n");
        for (i = 1; i <= Class_Num; i++)
        {
            for (j = 1; j <= Weak_Day; j++)
            {
                for (k = 1; k <= Day_Crs; k++)
                    System.out.print(Class_Crs[i][j][k]+" ");
                System.out.print("\n");
            }
            System.out.print("\n");
        }
        System.out.print("Tea" + "\n");
        for (i = 1; i <= Tea_Num; i++)
        {
            for (j = 1; j <= Weak_Day; j++)
            {
                for (k = 1; k <= Day_Crs; k++)
                    System.out.print(Tea_Crs[i][j][k]+" ");
                System.out.print("\n");
            }
            System.out.print("\n");
        }
        */
    }

    private void out_put_file(){
        int i, j, k, l, m, x, y;
        y = 0;
        String[] a= {"","星期一","星期二","星期三","星期四","星期五","星期六","星期日" };
        String[] b ={ " ","第一节","第二节", "第三节", "第四节", "第五节", "第六节", "第七节", "第八节" };
        File Filename5 = new File("C:\\Users\\18367\\IdeaProjects\\PK\\src\\XZPK\\time_class.csv");
        BufferedWriter outFile = null;
        try{
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(Filename5));
            outFile =  new BufferedWriter(write);
            outFile.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF }));
            for (i = 1; i <= Class_Num; i++)
            {
                outFile.write( Class.get(i-1) +"\n");
                for (j = 1; j <= Weak_Day; j++)
                {
                    outFile.write(a[j] + ",");
                    for (k = 1; k <= Day_Crs; k++)
                        if (Class_Crs[i][j][k] > 0)
                            outFile.write(Coursename.get(Class_Crs[i][j][k] - 1)+",");
                        else if (Class_Crs[i][j][k] == -2)
                            outFile.write("选考"+",");
                        else if (Class_Crs[i][j][k] == 0)
                            outFile.write(""+",");
                    outFile.write("\n");
                }
                outFile.write("\n");
            }
            outFile.flush();
            outFile.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        File Filename6 = new File("C:\\Users\\18367\\IdeaProjects\\PK\\src\\XZPK\\time_teacher.csv");
        BufferedWriter outFile1 = null;
        try{
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(Filename6));
            outFile1 =  new BufferedWriter(write);
            outFile1.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF }));
            for (i = 1; i <= Tea_Num; i++) {
                outFile1.write(Tea_Name.get(i - 1) + "\n");
                outFile1.write("" + ",");
                for (j = 1; j <= Day_Crs; j++) {
                    outFile1.write(b[j] + ",");
                }
                outFile1.write("\n");
                for (j = 1; j <= Weak_Day; j++) {
                    outFile1.write(a[j] + ",");
                    for (k = 1; k <= Day_Crs; k++)
                        if (Tea_Crs[i][j][k] > 0 && Tea_Crs[i][j][k] < 100)
                            outFile1.write(Class.get(Tea_Crs[i][j][k] - 1) + ",");
                        else if (Tea_Crs[i][j][k] >= 100)
                        {
                            Tea_Crs[i][j][k] = Tea_Crs[i][j][k] / 100;
                            for (x = 1; x <= Class_Num; x++)
                            {
                                if (Tea_Crs[i][j][k] == 0)
                                {
                                    break;
                                }
                                else
                                {
                                    y = Tea_Crs[i][j][k];
                                    outFile1.write(Class.get(Tea_Crs[i][j][k] % 100 - 1) + "\\");
                                    Tea_Crs[i][j][k] = Tea_Crs[i][j][k] / 100;
                                }
                            }
                            outFile1.write(Coursename.get(Class_Crs[y][j][k] - 1) + ",");
                        }
                        else if (Tea_Crs[i][j][k] == -1)//这个不会触发
                            outFile1.write("班会" + ",");
                        else if (Tea_Crs[i][j][k] == -2)
                            outFile1.write("备课" + ",");
                        else if (Tea_Crs[i][j][k] == -3)
                            outFile1.write("选考" + ",");
                        else
                            outFile1.write("" + ",");
                    outFile1.write("\n");
                }
            }
            outFile1.flush();
            outFile1.close();
        }catch (Exception e){
            e.printStackTrace();
        }


        /*
        File Filename7 = new File("C:\\Users\\18367\\IdeaProjects\\PK\\src\\XZPK\\time_stu.csv");
        BufferedWriter outFile2 = null;
        try{
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(Filename7));
            outFile2 =  new BufferedWriter(write);
            outFile2.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF }));
            for (i = 0; i < Stu_Num; i++)
            {
                outFile2.write("学号："+",");
                outFile2.write(Stu_ID[i]+","+",");
                outFile2.write("班级:"+","+ Grade.get(Stu_Class[i] - 1)+Class.get(Stu_Class[i]-1)+"\n");
                for (j = 1; j <= Weak_Day; j++)
                {
                    outFile2.write(a[j]+",");
                    for (k = 1; k <= Day_Crs; k++)
                        if (Stu_Crs[i][j][k] > 0)
                            outFile2.write(Coursename.get(Stu_Crs[i][j][k] - 1)+",");
                        else if (Stu_Crs[i][j][k] == -2)
                            outFile2.write(Stu_Course.get(i)+",");
                    outFile2.write("\n");
                }
                outFile2.write("\n");
            }
            outFile2.flush();
            outFile2.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        */
    }
    private int Last_Cal_Class_Score(int ndi, int knew){
        int pnd = Par[ndi];//属于哪一门课
        int kold = Color[ndi];
        int len;
        int f1 = 0;
        int maxk = Weak_Day / 2;//3天
        if (maxk < 3)
            maxk = 3;
        int i, j, k, l, len_lt, max, min;
        int x, y;
        int crsMr = 0;
        int crsAf = 0;
        int c_Num = Node_Course[ndi][0];//这节课的课程属性
        for (x = 1; x <= c_Num; x++)
        {
            //可以进行改进
            len = Set_Crs[MCls[pnd]][Node_Course[ndi][x]][0];
            for (i = 1; i <= Set_Crs[ MCls[pnd] ][ Node_Course[ndi][x] ][0]; i++)
            {
                if (Set_Crs[MCls[pnd]][Node_Course[ndi][x]][i] != ndi)
                    set[i] = Color[ Set_Crs[ MCls[pnd] ][ Node_Course[ndi][x] ][i] ];
                else
                    set[i] = knew;
            }
            int flag = 0;
            int dayZ = 0;
            int  m_c = Weak_Day*Day_Crs;//35天=KCL
            for (i = 1; i <= Weak_Day; i++)
            {
                daycr[i] = 0;//课时
                daylt[i] = 0;//连堂课
            }

            for (i = 1; i <= len; i++)
                daycr[PDay[set[i]]]++;//每天上几节

            for (i = 1; i <= len; i++)
            {
                if(PCr[set[i]] >= Day_Crs_Mr)
                {
                    crsMr++;//上午的课时数增加
                }
                else
                {
                    crsAf++;//下午的课时数增加
                }
            }

            int gcls = MCls[pnd];
            int gcr = MCrs[pnd];
            int gvtx=(gcls-1)*Crs_Num+gcr;

            if (Class_Crs_Num_L[gcls][gcr] > 0)//如果存在连堂课
            {
                for (i = 1; i <= m_c; i++)
                    All_cl[i] = 0;
                for (i = 1; i <= len; i++)
                    All_cl[set[i]] = 1;
                len_lt = 0;
                for (i = 1; i < m_c; i++)
                    if ((All_cl[i] == 1) && (All_cl[i + 1] == 1) && (PCr[i] != Day_Crs) && (PCr[i] != Day_Crs_Mr))
                    {
                        len_lt++;//连堂课数量+1
                        dayZ = PDay[i];//属于哪一天
                        daylt[dayZ]++;//这一天的连堂课数量+1
                        i++;
                    }
                if (len_lt < Class_Crs_Num_L[gcls][gcr])//连堂课少了
                {
                    Class_Rst[pnd][1] = 1;//连堂课少了
                    Class_Weak[pnd][0][1]++;
                    f1 += 50 * (Class_Crs_Num_L[gcls][gcr] - len_lt);
                }
                if (len_lt > Class_Crs_Num_L[gcls][gcr])//连堂课多了
                {
                    Class_Rst[pnd][2] = 1;//连堂课多了
                    Class_Weak[pnd][0][2]++;
                    f1 += 50 * (len_lt - Class_Crs_Num_L[gcls][gcr]);
                }

                max = -9999;
                min = 9999;
                for (i = 1; i <= Weak_Day; i++)
                {
                    if (daycr[i] > max)//一天最多上几节
                    {
                        max = daycr[i];
                    }
                    if (daycr[i] < min)//一天最少上几节
                    {
                        min = daycr[i];
                    }

                }
                if (max - min > 2)
                    f1 += 60;

                for (i = 1; i <= Weak_Day; i++)
                    if (daylt[i] == 0)//如果这一天没有连堂课
                    {
                        if (daycr[i] > Max_Day[pnd])//判断有没有超多一天最大的节数
                        {
                            Class_Rst[pnd][3] = 1;//多上了
                            Class_Weak[pnd][i][3]=1;//星期几触发了
                            for (j = 1; j <= len; j++)
                            {
                                if (PDay[set[j]] == i)
                                {
                                    Class_Crs_Rst[gcls][i][PCr[set[j]]] = 3;//哪几节触发了
                                }

                            }
                            f1 += 10;
                        }
                        if (daycr[i] < Min_Day[pnd])
                        {
                            Class_Rst[pnd][4] = 1;//少上了
                            Class_Weak[pnd][i][4]=1;//星期几触发了
                            for (j = 1; j <= len; j++)
                            {
                                if (PDay[set[j]] == i)
                                {
                                    Class_Crs_Rst[gcls][i][PCr[set[j]]] = 4;//哪几节触发了
                                }
                            }
                            f1 += 10;
                        }
                    }

                if (Ctwo == 1)//如果需要优先排课
                    if (Cct[MCrs[pnd]] == 1)
                    {
                        for (i = 1; i <= len; i++)
                            if (PCr[set[i]] > C_Time)
                            {
                                f1 += 3;
                                Class_Rst[pnd][5] = 1;//没有优先排
                                Class_Weak[pnd][PDay[set[i]]][5]=1;//哪一天
                                //System.out.print(MCls[pnd]+"  "+MCrs[pnd]+"\n");
                                Class_Crs_Rst[gcls][PDay[set[i]]][PCr[set[i]]] = 5;//哪一节
                            }

                    }

                if (Crs_Attri[pnd] == 1)
                {
                    //System.out.print("进来了");
                    l = 0;
                    for (i = 1; i <= len; i++)
                        if (PCr[set[i]] == 1)
                            l++;
                    if (l >= maxk)//有一半排在第一节
                    {
                        f1 += 4;
                        Class_Rst[pnd][6] = 1;
                        for (j = 1; j <= len; j++)
                            if (PCr[set[j]] == 1)
                            {
                                Class_Crs_Rst[gcls][PDay[set[j]]][PCr[set[j]]] = 6;
                                Class_Weak[pnd][PDay[set[j]]][6]=1;
                            }
                    }
                }

                //课程上下午分布（课程上下午分布过于集中）
                if(crsMr > Max_Mr[pnd] || crsAf >Max_Af[pnd])
                {
                    f1 +=2;
                    Class_Rst[pnd][9] = 1;
                    for (j = 1; j <= len; j++)
                    {
                        Class_Crs_Rst[gcls][PDay[set[j]]][PCr[set[j]]] = 9;
                        Class_Weak[pnd][PDay[set[j]]][9]=1;
                    }
                }

            }
            else//没有连堂课
            {
                if (Ctwo == 1)
                    if (Cct[MCrs[pnd]] == 1)
                    {
                        for (i = 1; i <= len; i++)
                            if (PCr[set[i]] > C_Time)
                            {
                                f1 += 3;
                                Class_Rst[pnd][5] = 1;
                                Class_Crs_Rst[gcls][PDay[set[i]]][PCr[set[i]]] = 5;
                                Class_Weak[pnd][PDay[set[i]]][5]=1;
                            }

                    }

                for (i = 1; i <= Weak_Day; i++)
                {
                    if (daycr[i] > Max_Day[pnd])
                    {
                        f1 += 10;
                        Class_Rst[pnd][3] = 1;
                        Class_Weak[pnd][i][3]=1;
                        for (j = 1; j <= len; j++)
                        {
                            if (PDay[set[j]] == i)
                                Class_Crs_Rst[gcls][i][PCr[set[j]]] = 3;
                        }
                    }
                    if (daycr[i] < Min_Day[pnd])
                    {
                        f1 += 10;
                        Class_Rst[pnd][4] = 1;
                        Class_Weak[pnd][i][4]=1;
                        for (j = 1; j <= len; j++)
                        {
                            if (PDay[set[j]] == i)
                                Class_Crs_Rst[gcls][i][PCr[set[j]]] = 4;
                        }
                    }

                }

                if (Crs_Attri[pnd] == 3)//3天不能连上
                {
                    for (i = 1; i <= Weak_Day - 2; i++)
                        if ((daycr[i] > 0) && (daycr[i + 1] > 0) && (daycr[i + 2] > 0))
                        {
                            f1 += 2;
                            Class_Rst[pnd][7] = 1;
                            Class_Weak[pnd][i][7]=1;
                            Class_Weak[pnd][i+1][7]=1;
                            Class_Weak[pnd][i+2][7]=1;
                            for (j = 1; j <= len; j++)
                            {
                                if (PDay[set[j]] == i || PDay[set[j]] == i + 1 || PDay[set[j]] == i + 2)
                                    Class_Crs_Rst[gcls][PDay[set[j]]][PCr[set[j]]] = 7;
                            }
                        }
                }
                else if (Crs_Attri[pnd] == 2)//两天不能连上
                {
                    for (i= 1; i <= Weak_Day - 1; i++)
                        if ((daycr[i] > 0) && (daycr[i + 1] > 0))
                        {
                            f1 += 2;
                            Class_Rst[pnd][8] = 1;
                            Class_Weak[pnd][i][8]=1;
                            Class_Weak[pnd][i+1][8]=1;
                            for (j = 1; j <= len; j++)
                            {
                                if (PDay[set[j]] == i || PDay[set[j]] == i + 1)
                                    Class_Crs_Rst[gcls][PDay[set[j]]][PCr[set[j]]] = 8;
                            }
                        }
                }

                if (Crs_Attri[pnd] == 1)//对于一周上5节课的课程
                {
                    //System.out.print("进来了");
                    l = 0;
                    for (i = 1; i <= len; i++)
                        if (PCr[set[i]] == 1)//连续半周排第一节
                            l++;
                    if (l >= maxk)
                    {
                        f1 += 4;
                        Class_Rst[pnd][6] = 1;
                        for (j = 1; j <= len; j++)
                            if (PCr[set[j]] == 1)
                            {
                                Class_Crs_Rst[gcls][PDay[set[j]]][PCr[set[j]]] = 6;
                                Class_Weak[pnd][PDay[set[j]]][6]=1;
                            }
                    }
                }

                if(crsMr > Max_Mr[pnd] || crsAf >Max_Af[pnd])
                {
                    f1 +=2;
                    Class_Rst[pnd][9] = 1;
                    for (j = 1; j <= len; j++)
                    {
                        Class_Crs_Rst[gcls][PDay[set[j]]][PCr[set[j]]] = 9;
                        Class_Weak[pnd][PDay[set[j]]][9]=1;
                    }
                }

            }
        }
        return f1;
    }

    private int Last_Cal_JP_Score(int ndi, int knew, int t){
        int crs = Par[ndi];//是哪一门课
        int x,y,z;
        int i, j, k, f1, l, m, n, max_pre, min_l, min_c, max_c;
        f1 = 0;
        int[] Tea_set=new int[30];
        int[] maxc=new int[10 + 1];
        int[] minc=new int[10 + 1];
        int[] maxl=new int[10 + 1];
        int[] minl=new int[10 + 1];
        int len;
        int len_l;
        for (i = 1; i <= Set_cl[crs][0]; i++) //记录初始的状态
        {
            set[i] = Crs_srt_cl[crs][i];//这一门课的所有课
        }

        for (i = 1; i <= Set_cl[crs][0]; i++)
            if (Crs_srt_cl[crs][i] == Color[ndi])
            {
                Crs_srt_cl[crs][i] = knew;
                break;
            }//每个都着不同的色了已经
        bubble_sort(Crs_srt_cl[crs], Set_cl[crs][0]);//这门课重新排序

        for (x = 1; x <= Tea_CP_Sign[t][0]; x++)//一个老师的所有课
        {
            //循环次数太多
            len = 0;
            for (y = 1; y <= Tea_CP[t][0]; y++)
            {
                if (Tea_CP_Sign[t][y] == x)//属于需要比较的
                {
                    len++;
                    Tea_set[len] = Tea_CP[t][y];
                }
            }
            Tea_set[0] = len;//有多少门课
            int pnd = Tea_set[1];//新值

            if (Class_Crs_Num_L[MCls[pnd]][MCrs[pnd]] > 0)
            {
                len_l = 0;
                //第一种判定方式
                for (j = 1; j <= 10; j++)
                {
                    maxc[j] = -1;
                    minc[j] = 99999;
                }
                for (j = 1; j <= len; j++)
                {
                    k = Tea_set[j]; //老师教的这门课，有几门课需要进行比较
                    l = 0;
                    for (i = 2; i <= Set_cl[pnd][0]; i++)
                    {
                        if (((Crs_srt_cl[k][i] - Crs_srt_cl[k][i - 1]) == 1) && (PDay[Crs_srt_cl[k][i]] == PDay[Crs_srt_cl[k][i - 1]]))
                        {
                            l++;//每一次进入计数
                            if (Crs_srt_cl[k][i - 1] < minc[l])//第l次连课的第一节在什么时候上
                                minc[l] = Crs_srt_cl[k][i-1];
                            if (Crs_srt_cl[k][i] > maxc[l])//第l次连课的第二节在什么时候上
                                maxc[l] = Crs_srt_cl[k][i];
                            //break;
                        }
                    }
                    if (len_l < l)
                    {
                        len_l = l;//有几次连堂课
                    }
                }


                int[] a=new int[30];
                l = 0;
                for (j = 1; j <= len; j++)
                {
                    k = Tea_set[j];//哪门课
                    for (i = 1; i <= Set_cl[pnd][0]; i++)
                        a[i] = 0;
                    for (i = 2; i <= Set_cl[pnd][0]; i++)
                    {
                        if (((Crs_srt_cl[k][i] - Crs_srt_cl[k][i - 1]) == 1) && (PDay[Crs_srt_cl[k][i]] == PDay[Crs_srt_cl[k][i - 1]]))
                        {
                            a[i] = 1;//找出所有的连堂课
                            a[i - 1] = 1;
                        }
                    }
                    for (i = 1; i <= len_l; i++)//对于第一节课
                    {
                        for (m = 1; m <= Set_cl[pnd][0]; m++)
                        {
                            if ((a[m] == 0) && (Crs_srt_cl[k][m] < maxc[i]) && (Crs_srt_cl[k][m] > minc[i])) //非连堂课去进行判定//不该是这样去检验
                                l++;
                        }
                    }
                }
                if (l > 0)
                {
                    f1 = f1+3;
                    Tea_Rst[t][1] = 1;

                    k = Tea_set[j];//哪门课
                    for (i = 1; i <= Set_cl[pnd][0]; i++)
                        a[i] = 0;
                    for (i = 2; i <= Set_cl[pnd][0]; i++)
                    {
                        if (((Crs_srt_cl[k][i] - Crs_srt_cl[k][i - 1]) == 1) && (PDay[Crs_srt_cl[k][i]] == PDay[Crs_srt_cl[k][i - 1]]))
                        {
                            a[i] = 1;//找出所有的连堂课
                            a[i - 1] = 1;
                        }
                    }
                    for (i = 1; i <= len_l; i++)//对于第一节课
                    {
                        for (m = 1; m <= Set_cl[pnd][0]; m++)
                        {
                            if ((a[m] == 0) && (Crs_srt_cl[k][m] < maxc[i]) && (Crs_srt_cl[k][m] > minc[i])) //处于连堂课之间的
                            {
                                Tea_Weak[t][PDay[Crs_srt_cl[k][m]]][1] = 1;
                                Tea_Crs_Rst[t][PDay[Crs_srt_cl[k][m]]][PCr[Crs_srt_cl[k][m]]] = 1;
                            }
                        }
                    }

                }
                else
                    f1 = f1+0;


                //第二种判定方式
                for (j = 1; j <= 10; j++)
                {
                    maxl[j] = -1;
                    minl[j] = 99999;
                }
                for (j = 1; j <= len; j++)
                {
                    k = Tea_set[j];
                    for (i = 1; i <= Set_cl[pnd][0]; i++)
                        a[i] = 0;
                    for (i = 2; i <= Set_cl[pnd][0]; i++)
                    {
                        if (((Crs_srt_cl[k][i] - Crs_srt_cl[k][i - 1]) == 1) && (PDay[Crs_srt_cl[k][i]] == PDay[Crs_srt_cl[k][i - 1]]))
                        {
                            a[i] = 1;
                            a[i - 1] = 1;
                        }
                    }
                    l = 0;
                    for (i = 1; i <= len_l; i++)
                    {
                        for (m = 1; m <= Set_cl[pnd][0]; m++)
                        {
                            if ((a[m] == 0) && (Crs_srt_cl[k][m] < maxc[i])) //最后一节连堂课之前有几节课，一定要一样
                                l++;
                        }
                        if (l > maxl[i])
                            maxl[i] = l;
                        if (l < minl[i])
                            minl[i] = l;
                    }
                }
                for (i = 1; i <= len_l; i++)
                {
                    f1 = f1 + 2 * (maxl[i] - minl[i]);  //另一种判定方式，不同课连堂课之前上的课的数量
                    if(maxl[i] - minl[i]>0)
                    {
                        Tea_Rst[t][2] = 1;// 连堂课前后不平齐
                        Tea_Weak[t][0][2]++;
                    }
                }
                //教案平齐（没连堂的教案平齐和连堂教案平齐）
                if (Class_Crs_Num_L[MCls[pnd]][MCrs[pnd]] > 1)//多次连堂课
                {
                    min_l = 9999;
                    for (j = 1; j <= len; j++)
                    {
                        k = Tea_set[j];
                        for (i = 1; i <= Set_cl[pnd][0]; i++)
                            a[i] = 0;
                        for (i = 2; i <= Set_cl[pnd][0]; i++)
                        {
                            if (((Crs_srt_cl[k][i] - Crs_srt_cl[k][i - 1]) == 1) && (PDay[Crs_srt_cl[k][i]] == PDay[Crs_srt_cl[k][i - 1]]))//连堂课
                            {
                                a[i] = 1;
                                a[i - 1] = 1;
                            }
                        }
                        l = 0;
                        for (i = 1; i <= Set_cl[pnd][0]; i++)
                            if ((a[i] == 1))
                            {
                                l++;//l肯定是双数
                                JP_KC[j][l] = Crs_srt_cl[k][i];//记录连堂课
                            }
                        if (l < min_l)
                            min_l = l/2;//最少的连堂的有几节课/2
                    }
                    for (i = 1; i <= min_l-1; i++)//连堂课的节数，从第二节开始
                    {
                        max_c = -1;
                        min_c = 99999;
                        for (j = 1; j <= len; j++)
                        {
                            if (JP_KC[j][i*2+1] < min_c)
                                min_c = JP_KC[j][i*2+1];
                            if (JP_KC[j][i*2] > max_c)
                                max_c = JP_KC[j][i*2];//最早开始和最晚开始的两节课
                        }
                        if (min_c < max_c)//最早开始的要比前一节最晚开始的早，不对
                        {
                            f1 += 2;
                            Tea_Rst[t][5] = 1;
                            Tea_Weak[t][PDay[min_c]][5] = 1;
                            Tea_Weak[t][PDay[max_c]][5] = 1;
                            Tea_Crs_Rst[t][PDay[min_c]][PCr[min_c]] = 5;
                            Tea_Crs_Rst[t][PDay[max_c]][PCr[max_c]] = 5;
                        }

                    }
                }
                //进行非连堂课的教案平齐
                min_l = 9999;
                for (j = 1; j <= len; j++)
                {
                    k = Tea_set[j];
                    for (i = 1; i <= Set_cl[pnd][0]; i++)
                        a[i] = 0;
                    for (i = 2; i <= Set_cl[pnd][0]; i++)
                    {
                        if (((Crs_srt_cl[k][i] - Crs_srt_cl[k][i - 1]) == 1) && (PDay[Crs_srt_cl[k][i]] == PDay[Crs_srt_cl[k][i - 1]]))//非连堂课
                        {
                            a[i] = 1;
                            a[i - 1] = 1;
                        }
                    }
                    l = 0;
                    for (i = 1; i <= Set_cl[pnd][0]; i++)
                        if ((a[i] == 0))
                        {
                            l++;
                            JP_KC[j][l] = Crs_srt_cl[k][i];//没有连课的这几节
                        }
                    if (l < min_l)
                        min_l = l;//没连堂的有几节课
                }
                max_pre = -1;
                for (j = 1; j <= len; j++)
                {
                    if (JP_KC[j][1] > max_pre)
                        max_pre = JP_KC[j][1];//最晚开始的最早的一节课
                }

                for (i = 2; i <= min_l; i++)//连堂课的节数，从第二节开始
                {
                    max_c = -1;
                    min_c = 99999;
                    for (j = 1; j <= len; j++)
                    {
                        if (JP_KC[j][i] < min_c)
                            min_c = JP_KC[j][i];
                        if (JP_KC[j][i] > max_c)
                            max_c = JP_KC[j][i];//最早开始和最晚开始的两节课
                    }
                    if (min_c < max_pre || PDay[min_c] == PDay[max_pre])//最早开始的要比前一节最晚开始的早，不对
                    {
                        f1 += 2;
                        Tea_Rst[t][3] = 1;
                        Tea_Weak[t][PDay[min_c]][3] = 1;
                        Tea_Weak[t][PDay[max_pre]][3] = 1;
                        Tea_Crs_Rst[t][PDay[min_c]][PCr[min_c]] = 3;
                        Tea_Crs_Rst[t][PDay[max_pre]][PCr[max_pre]] = 3;
                    }
                    max_pre = max_c;//转换
                }
            }
            else
            {
                //如果不触发连堂课，直接判断教案平齐
                max_pre = -1;
                for (j = 1; j <= len; j++)
                {
                    k = Tea_set[j];
                    if (Crs_srt_cl[k][1] > max_pre)
                        max_pre = Crs_srt_cl[k][1];
                }
                for (i = 2; i <= Set_cl[pnd][0]; i++)
                {
                    max_c = -1;
                    min_c = 99999;
                    for (j = 1; j <= len; j++)
                    {
                        k = Tea_set[j];
                        if (Crs_srt_cl[k][i] < min_c)
                            min_c = Crs_srt_cl[k][i];
                        if (Crs_srt_cl[k][i] > max_c)
                            max_c = Crs_srt_cl[k][i];
                    }
                    if (min_c < max_pre || PDay[min_c] == PDay[max_pre])
                    {
                        f1 += 3;
                        Tea_Rst[t][3] = 1;
                        Tea_Weak[t][PDay[min_c]][3] = 1;
                        Tea_Weak[t][PDay[max_pre]][3] = 1;
                        Tea_Crs_Rst[t][PDay[min_c]][PCr[min_c]] = 3;
                        Tea_Crs_Rst[t][PDay[max_pre]][PCr[max_pre]] = 3;
                    }
                    max_pre = max_c;
                }
            }

        }
        //算完之后回归原位
        for (i = 1; i <= Set_cl[crs][0]; i++)
        {
            Crs_srt_cl[crs][i] = set[i];//这个pnd不是原来的pnd了
        }
        return f1;
    }

    private void Out_Put_Erro_BTH(int Rslt[]){
        int i, j, k, m, l;
        for (i = 1; i <= N_vtx; i++)
        {
            Color[i] = Rslt[i];
        }
        for (i = 0; i <= Tea_Num; i++)
        {
            for (j = 0; j <= Weak_Day; j++)
            {
                for (k = 0; k <= Day_Crs; k++)
                    Tea_Crs_Rst[i][j][k] = 0;
            }
        }

        for (i = 0; i <= Class_Num; i++)
        {
            for (j = 0; j <= Weak_Day; j++)
            {
                for (k = 0; k <= Day_Crs; k++)
                    Class_Crs_Rst[i][j][k] = 0;
            }
        }
        for (i = 0; i <= Class_Num*Crs_Num; i++)
        {
            for (j = 0; j <= Weak_Day; j++)
            {
                for (k = 0; k <= 9; k++)
                    Class_Weak[i][j][k] = 0;
            }
        }
        for (i = 0; i <= Tea_Num; i++)
        {
            for (j = 0; j <= Weak_Day; j++)
            {
                for (k = 0; k <= 5; k++)
                    Tea_Weak[i][j][k] = 0;
            }
        }
        Clear_Delta_Matrix();
        Build_Delta_Matrix();

        f_best = f;

        fc = 0;
        for (i = 1; i <= MNum; i++)
        {
            m = Set_cl[i][1];
            Class_Score[i] = Last_Cal_Class_Score(m, Color[m]);
            fc += Class_Score[i];
            if (Class_Score[i] != 0)
            {
                System.out.print("可能不合理的课程 班级: " + Class.get(MCls[i]-1) + "班， 课程：" + MCrs[i] + " Score = " + Class_Score[i] + "\n");
            }

        }


        Sort_Src();
        for (i = 1; i <= Tea_Num; i++)
            Tea_JP_Score[i] = 0;
        for (i = 1; i <= MNum; i++)
        {
            m = Set_cl[i][1];//是哪一节课
            for (j = 1; j <= Node_Tea[m][0]; j++)
            {
                if (Node_Tea[m][j] > 0)
                {
                    Tea_JP_Score[Node_Tea[m][j]] = Last_Cal_JP_Score(m, Color[m], Node_Tea[m][j]);//哪位老师
                }
            }
        }

        for (i = 1; i <= Tea_Num; i++)
            if (Tea_JP_Score[i] != 0)
            {
                System.out.print( "可能教案不平齐的老师: " + Tea_Name.get(i-1) + " Score = " + Tea_JP_Score[i] + "\n");
            }


        set_tea_Src();

        for (i = 1; i <= Tea_Num; i++)
        {
            l = 0;
            for (j = 1; j <= Weak_Day; j++)
            {
                l += Tea_Scr[i][j];
                if(Tea_Scr[i][j]>0)
                {
                    Tea_Weak[i][j][4]=1;
                }
            }
            if (l > 0)
            {
                Tea_Rst[i][4]=1;
                System.out.print("可能时间不集中的老师: " + Tea_Name.get(i-1) + " Score = " + l + "\n");
            }

        }

        f_tea = 0;
        for (i = 1; i <= Tea_Num; i++)
        {
            f_tea += Tea_Score[i];
        }

        fc += f_tea;
        fc_best = fc;
        System.out.print("硬约束 f = " + f + " 软约束 fc = " + fc + "\n");

        for (i = 1; i <= Tea_Num; i++)
        {
            l = 0;
            for (j = 1; j <= Weak_Day; j++)
            {
                for (k = 1; k <= Day_Crs; k++)
                {
                    if (Tea_Crs[i][j][k] != 0)
                    {
                        l++;
                    }
                }
            }
            System.out.print("教师 " + Tea_Name.get(i-1) + " 一周上 " + l + " 节课" + "\n");
        }
    }

    public void to_json(String path1, String path2){

        int i, j, k;
        int x, y, z;
        int l, m, n, o, p;
        double v = 0;
        int[] Class_Rst_V = { 0,0,0,0,0,0,0,0,0,0 };//小项
        int[] Tea_Rst_V = { 0,0,0,0,0,0 };
        int[] Class_V = { 0,0,0,0,0 };//大项
        int[] Tea_V = { 0,0,0,0 };
        int[][] DY_Class = { { 0,0,0,0 },{ 2,1,2,0 },{ 2,3,4,0 },{ 1,5,0,0 },{ 4,6,7,8,9 } };
        int[][] DY_Tea = { { 0,0,0 },{ 3,1,2,5 },{ 1,3,0 },{ 1,4,0 } };
        String[] a = { "","LTK_limit","Day_Crs_limit","Priority_class_limit","Weak_Crs_distributed" };
        String[] b = { "","LTK_JP","FLTK_JP","JZSK" };
        String[] a1 = { "","连堂课限制","日课时限制","优先排课限制","周课时分布" };
        String[] b1 = { "","连堂课教案平齐","非连堂课教案平齐","集中授课" };
        String[] c = { "","连堂课少上","连堂课多上","超出天最大限制","低于天最小限制","没有满足优先排","第一节安排过多","三天连上","两天连上","上下午分布过于紧密" };
        String[] d = { "","两次连堂课之间有课","连堂课前后教案不平齐","普通课教案不平齐","授课不集中" ,"连堂课教案不平齐"};
        String[] w = {"","星期一","星期二","星期三","星期四","星期五","星期六","星期日"};

        for(i=1;i<=MNum;i++)
        {
            for(j=1;j<=Weak_Day;j++)
            {
                for(k=1;k<=9;k++)
                {
                    Class_Weak[i][0][k] += Class_Weak[i][j][k];//判断是否有触发
                }
            }
        }
        for(i=1;i<=Tea_Num;i++)
        {
            for(j=1;j<=Weak_Day;j++)
            {
                for(k=1;k<=5;k++)
                {
                    Tea_Weak[i][0][k]+=Tea_Weak[i][j][k];//判断是否有触发
                }
            }
        }

        for(i = 1; i <= MNum; i++)
        {
            for (j = 1; j <= 9 ; j++)
            {
                Class_Rst_V[j] += Class_Weak[i][0][j];//8条规则分别的值
            }
        }
        l = 0;
        for (i = 1; i <= 4; i++)
        {
            for (j = 1; j <= DY_Class[i][0]; j++)
            {
                Class_V[i] += Class_Rst_V[DY_Class[i][j]];//四条大规则的值
                System.out.print("Class_V[i]" + Class_V[i] + "\n");
            }
            if (Class_V[i] > 0)
                l++;
        }
        for (i = 1; i <= Tea_Num; i++)
        {
            for (j = 1; j <= 5; j++)
            {
                Tea_Rst_V[j] += Tea_Weak[i][0][j];//5条规则分别的值
            }
        }
        m = 0;
        for (i = 1; i <= 3; i++)
        {
            for (j = 1; j <= DY_Tea[i][0]; j++)
            {
                Tea_V[i] += Tea_Rst_V[DY_Tea[i][j]];//三条大规则的值
            }
            if (Tea_V[i] > 0)
                m++;
        }
        for(i=1;i<=4;i++)//5条大规则
        {
            v+=100.0 - (Class_V[i] * 100.0) / (MNum*Weak_Day*1.0);
        }
        for(i=1;i<=3;i++)//3条大规则
        {
            v+=100.0 - (Tea_V[i] * 100.0)/ (Tea_Num*Weak_Day*1.0);
        }
        v=v/7;
        File Filename8 = new File(path1);
        BufferedWriter outFile = null;
        try{
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(Filename8));
            outFile =  new BufferedWriter(write);
            //outFile.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF }));
            outFile.write("{"+"\n");
            outFile.write("    \"Course_quality\":" + "\"" + v + "%\"," + "\n");
            if(v!=100)
            {
                outFile.write( "    \"Quality_description\":" + "\"在排课规则中，班级不排课时间、课程不排课时间、教师不排课时间、合班课");
                for (i = 1; i <= 4; i++)
                {
                    if (Class_V[i] == 0)
                    {
                        outFile.write("、" + a1[i]);
                    }
                }
                for (i = 1; i <= 3; i++)
                {
                    if (Tea_V[i] == 0)
                    {
                        outFile.write("、" + b1[i]);
                    }
                }
                outFile.write(" 等规则的满足率为100%,");
                n = 0;
                for (i = 1; i <= 4; i++)
                {
                    if (Class_V[i] > 0)//违反
                    {
                        outFile.write(a1[i]);
                        n++;
                        if (n == l + m)
                            break;
                        outFile.write("、");
                    }

                }
                for (i = 1; i <= 3; i++)
                {
                    if (Tea_V[i] > 0)//违反
                    {
                        outFile.write(b1[i]);
                        n++;
                        if (n == l + m)
                            break;
                        outFile.write("、");
                    }

                }
                System.out.print("成功"+"\n");
                outFile.write("等规则仍有不满足\"," + "\n");

            }
            else
            {
                outFile.write("    \"Quality_description\":" + "\"所有条件都满足，排课质量为100%\","+"\n");
            }

            o = 0;
            for (i = 1; i <= 4; i++)
            {
                n = 0;
                outFile.write("    \"" + a[i] + "\":{" + "\n");
                outFile.write("       \"Rule_satisfaction_rate\":" + "\"" + (100.0 - (Class_V[i] * 100.0) / (MNum*Weak_Day*1.0)) + "%\"," + "\n");
                outFile.write("       \"Unsatisfaction\":[");
                for (j = 1; j <= DY_Class[i][0]; j++)
                {
                    for (k = 1; k <= MNum; k++)
                    {
                        if (Class_Rst[k][DY_Class[i][j]] == 1)//一旦触发
                        {
                            System.out.print( MCls[k] + "   " + MCrs[k] + "\n");
                            outFile.write("\"" + Class.get(MCls[k] - 1) + Coursename.get(MCrs[k] - 1) + "课");
                            p=0;
                            for (y = 1; y <= Weak_Day; y++)
                            {
                                if(Class_Weak[k][y][DY_Class[i][j]]==1)
                                {
                                    outFile.write(w[y]);
                                    p++;
                                    if(p == Class_Weak[k][0][DY_Class[i][j]])
                                        break;
                                    outFile.write("、");
                                }
                            }
                            outFile.write("存在" + c[DY_Class[i][j]] + "的情况");
                            outFile.write("\"");
                            n++;
                            if (n == Class_V[i])
                                break;
                            outFile.write(",");
                        }
                    }
                }
                outFile.write( "]," + "\n");
                n = 0;
                outFile.write("       \"Problematic_section\":[");
                for (j = 1; j <= DY_Class[i][0]; j++)
                {
                    for (x = 1; x <= Class_Num; x++)
                    {
                        for (y = 1; y <= Weak_Day; y++)
                        {
                            for (z = 1; z <= Day_Crs; z++)
                            {

                                if (Class_Crs_Rst[x][y][z] == DY_Class[i][j])
                                {
                                    if (n>0)
                                        outFile.write(",");
                                    outFile.write( "{\"Class\":" + x + ",\"Day\":" + y + ",\"Crs\":" + z + "}");
                                    n++;
                                }

                            }
                        }
                    }
                }
                outFile.write("]" + "\n");
                outFile.write("    }");
                o++;
                if (o == 7)
                    break;
                outFile.write("," +"\n");
            }
            for (i = 1; i <= 3; i++)
            {
                //if (Tea_V[i] > 0)
                {
                    n = 0;
                    outFile.write("    \"" + b[i] + "\":{" + "\n");
                    outFile.write("       \"Rule_satisfaction_rate\":" + "\"" + (100.0 - (Tea_V[i] * 100.0)/ (Tea_Num*Weak_Day*1.0)) + "%\"," + "\n");
                    outFile.write("       \"Unsatisfaction\":[");
                    for (j = 1; j <= DY_Tea[i][0]; j++)
                    {
                        for (k = 1; k <= Tea_Num; k++)
                        {
                            if (Tea_Rst[k][DY_Tea[i][j]] == 1)
                            {
                                outFile.write("\"" + Tea_Name.get(k-1));
                                p=0;
                                for (y = 1; y <= Weak_Day; y++)
                                {
                                    if(Tea_Weak[k][y][DY_Tea[i][j]]==1)
                                    {
                                        outFile.write(w[y]);
                                        p++;
                                        if(p == Tea_Weak[k][0][DY_Tea[i][j]])
                                            break;
                                        outFile.write("、");
                                    }
                                }
                                outFile.write("存在" + d[DY_Tea[i][j]] + "的情况\"");
                                n++;
                                if (n == Tea_V[i])
                                    break;
                                outFile.write(",");
                            }
                        }
                    }
                    outFile.write("]," + "\n");
                    n = 0;
                    outFile.write("       \"Problematic_section\":[");
                    for (j = 1; j <= DY_Tea[i][0]; j++)
                    {
                        for (x = 1; x <= Tea_Num; x++)
                        {
                            for (y = 1; y <= Weak_Day; y++)
                            {
                                for (z = 1; z <= Day_Crs; z++)
                                {

                                    if (Tea_Crs_Rst[x][y][z] == DY_Tea[i][j])
                                    {
                                        if (n>0)
                                            outFile.write(",");
                                        outFile.write("{\"Tea\":" + x + ",\"Day\":" + y + ",\"Crs\":" + z + "}");
                                        n++;
                                    }

                                }
                            }
                        }
                    }
                    outFile.write("]" + "\n");
                    outFile.write("    }");
                    o++;
                    if (o == 7)
                        break;
                    outFile.write("," + "\n");
                }
            }
            outFile.write("\n");
            outFile.write("}");
            outFile.flush();
            outFile.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        File Filename9 = new File(path2);//不排课时间、课程
        BufferedWriter outFile1 = null;
        try {
            OutputStreamWriter write1 = new OutputStreamWriter(new FileOutputStream(Filename9));
            outFile1 =  new BufferedWriter(write1);
            outFile1.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF }));
            outFile1.write("{" + "\n");
            outFile1.write("    \"Weak_Crs\":" + "\"" + KCL + "\"," + "\n");
            outFile1.write("    \"Result\":" + "[" + "\n");
            l = 0;
            for (i = 1; i <= Class_Num; i++)
            {
                for (j = 1; j <= Weak_Day; j++)
                {
                    for (k = 1; k <= Day_Crs; k++)
                    {
                        outFile1.write("       {");
                        outFile1.write("\"Class\":\"" + Class.get(i-1) + "\",\"Day\":" + j + ",\"Crs\":" + k + ",");
                        outFile1.write("\"Course\":\"");
                        if (Class_Crs[i][j][k] > 0)
                        {
                            outFile1.write( CourseID.get(Class_Crs[i][j][k] - 1) + "\",");
                            outFile1.write("\"Tea\":\"" + Class_Tea[i][Class_Crs[i][j][k]] + "\",");
                        }
                        else if (Class_Crs[i][j][k] == 0)
                        {
                            outFile1.write("\"" + ",");
                            outFile1.write("\"Tea\":" + "\"\",");
                        }
                        else if(Class_Crs[i][j][k] == -2)
                        {
                            outFile1.write("选考\"" + ",");
                            outFile1.write("\"Tea\":" + "\"\",");
                        }
                        outFile1.write("\"Course_scheduling\":[");

                        for (x = 1; x <= Crs_Num; x++)
                        {
                            outFile1.write("\""+Crs_AvrT[x][j][k]+"\"");
                            if (x == Crs_Num)
                                break;
                            outFile1.write(",");
                        }
                        outFile1.write("],");

                        outFile1.write("\"Tea_scheduling\":[");
                        for (x = 1; x <= Tea_Num; x++)
                        {
                            outFile1.write("\""+Tea_AvrT[x][j][k]+"\"");
                            if (x == Tea_Num)
                                break;
                            outFile1.write(",");
                        }

                        outFile1.write("]");
                        outFile1.write("}");
                        l++;
                        if (l == Class_Num*Weak_Day*Day_Crs)
                        {
                            outFile1.write("\n");
                            break;
                        }
                        outFile1.write("," + "\n");
                    }
                }
            }
            outFile1.write("    ]" + "\n");
            outFile1.write("}" + "\n");
            outFile1.flush();
            outFile1.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public ResultOutput startArrageSceldue()throws MyException
    {
        int i, j, k, minl, gl, l, flag = 0;
        check_kc();
        check_HBK();
        set_HB();//设置合班课
        //graph.set_XK();//设置选考课
        //graph.set_ZB();//设置走班课
        set_CD();
        con_struct_graph();
        set_YPK();
        set_avbtime();
        set_crs_attrib();
        System.out.print("Begin"+"\n");
        minl = 9999999;
        TTL = 2 * Class_Num;
        ini_tabu();
        long startTime=System.currentTimeMillis();   //获取开始时间
        for (k = 1; k <= 10; k++)
        {
            System.out.print("k=" + k );
            Get_Initial_solution(5000);
            l = 0;
            while (f != 0 && l<200)
            {
                Get_Initial_solution(5000);
                l++;
            }
            if(l >= 200)
            {
                throw new MyException("由隐性冲突造成的可排课时间小于总课时，请减少部分约束");
            }

            gl = Compoud_Tabu_Search_JP(5000);//教案平齐
            //graph.get_reslut();
            gl = Compoud_Tabu_Search_BTH(10000);//集中排课

            //get_reslut1();
            if (gl < minl)
            {
                minl = gl;
                flag = 1;
                for (i = 1; i <= N_vtx; i++)
                    Final_Color[i] = Best_Color[i];
            }
            System.out.print("|--------------| minl = " + minl + " |---------| gl = " + gl + "\n");
            long endTime=System.currentTimeMillis(); //获取结束时间
            if((endTime-startTime)/1000 >= 300)
            {
                throw new MyException("数据量太大，算法运行时间过长");
            }
        }
        if (flag == 0)
        {
            throw new MyException("生成的方案是非法方案，约束太多导致,请减少约束");
        }
        for (i = 1; i <= N_vtx; i++)
            Color[i] = Final_Color[i];
        get_reslut();
        Out_Put_Erro_BTH(Final_Color);
        get_reslut();
        out_put_file();
        //to_json("C:\\Users\\18367\\IdeaProjects\\PK\\src\\XZPK\\result1.json","C:\\Users\\18367\\IdeaProjects\\PK\\src\\XZPK\\result2.json");
        return getResultData();
    }

    private ResultOutput getResultData(){
        int i, j, k;
        int x, y, z;
        int l, m, n, o, p;
        y = 0;
        ResultOutput outputData = new ResultOutput();

        double v = 0;
        int[] Class_Rst_V = { 0,0,0,0,0,0,0,0,0,0 };//小项
        int[] Tea_Rst_V = { 0,0,0,0,0,0 };
        int[] Class_V = { 0,0,0,0,0 };//大项
        int[] Tea_V = { 0,0,0,0 };
        int[][] DY_Class = { { 0,0,0,0 },{ 2,1,2,0 },{ 2,3,4,0 },{ 1,5,0,0 },{ 4,6,7,8,9 } };
        int[][] DY_Tea = { { 0,0,0 },{ 3,1,2,5 },{ 1,3,0 },{ 1,4,0 } };
        String[] a = { "","LTK_limit","Day_Crs_limit","Priority_class_limit","Weak_Crs_distributed" };
        String[] b = { "","LTK_JP","FLTK_JP","JZSK" };
        String[] a1 = { "","连堂课限制","日课时限制","优先排课限制","周课时分布" };
        String[] b1 = { "","连堂课教案平齐","非连堂课教案平齐","集中授课" };
        String[] c = { "","连堂课少上","连堂课多上","超出天最大限制","低于天最小限制","没有满足优先排","第一节安排过多","三天连上","两天连上" ,"上下午分布过于紧密"};
        String[] d = { "","两次连堂课之间有课","连堂课前后教案不平齐","普通课教案不平齐","授课不集中" ,"连堂课教案不平齐"};
        String[] w = {"","星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
        for(i=1;i<=MNum;i++)
        {
            for(j=1;j<=Weak_Day;j++)
            {
                for(k=1;k<=9;k++)
                {
                    Class_Weak[i][0][k] += Class_Weak[i][j][k];//判断是否有触发
                }
            }
        }
        for(i=1;i<=Tea_Num;i++)
        {
            for(j=1;j<=Weak_Day;j++)
            {
                for(k=1;k<=5;k++)
                {
                    Tea_Weak[i][0][k]+=Tea_Weak[i][j][k];//判断是否有触发
                }
            }
        }

        for(i = 1; i <= MNum; i++)
        {
            for (j = 1; j <= 9; j++)
            {
                Class_Rst_V[j] += Class_Weak[i][0][j];//8条规则分别的值
            }
        }
        l = 0;
        for (i = 1; i <= 4; i++)
        {
            for (j = 1; j <= DY_Class[i][0]; j++)
            {
                Class_V[i] += Class_Rst_V[DY_Class[i][j]];//四条大规则的值
            }
            if (Class_V[i] > 0)
                l++;
        }
        for (i = 1; i <= Tea_Num; i++)
        {
            for (j = 1; j <= 5; j++)
            {
                Tea_Rst_V[j] += Tea_Weak[i][0][j];//5条规则分别的值
            }
        }
        m = 0;
        for (i = 1; i <= 3; i++)
        {
            for (j = 1; j <= DY_Tea[i][0]; j++)
            {
                Tea_V[i] += Tea_Rst_V[DY_Tea[i][j]];//三条大规则的值
            }
            if (Tea_V[i] > 0)
                m++;
        }
        for(i=1;i<=4;i++)//5条大规则
        {
            v+=100.0 - (Class_V[i] * 100.0) / (MNum*Weak_Day*1.0);
        }
        for(i=1;i<=3;i++)//3条大规则
        {
            v+=100.0 - (Tea_V[i] * 100.0)/ (Tea_Num*Weak_Day*1.0);
        }
        v=v/7;
        String Course_quality = v + "%";
        String Quality_description;
        if(v!=100)
        {
            Quality_description = "在排课规则中，班级不排课时间、课程不排课时间、教师不排课时间、合班课";
            for (i = 1; i <= 4; i++)
            {
                if (Class_V[i] == 0)
                {
                    Quality_description = Quality_description +"、" + a1[i];
                }
            }
            for (i = 1; i <= 3; i++)
            {
                if (Tea_V[i] == 0)
                {
                    Quality_description = Quality_description + "、" + b1[i];
                }
            }
            Quality_description = Quality_description + " 等规则的满足率为100%,";
            n = 0;
            for (i = 1; i <= 4; i++)
            {
                if (Class_V[i] > 0)//违反
                {
                    Quality_description = Quality_description + a1[i];
                    n++;
                    if (n == l + m)
                        break;
                    Quality_description = Quality_description + "、";
                }
            }
            for (i = 1; i <= 3; i++)
            {
                if (Tea_V[i] > 0)//违反
                {
                    Quality_description = Quality_description + b1[i];
                    n++;
                    if (n == l + m)
                        break;
                    Quality_description = Quality_description + "、";
                }
            }
            Quality_description = Quality_description + "等规则仍有不满足";
        }
        else
        {
            Quality_description = "所有条件都满足，排课质量为100%";
        }

        outputData.setCourseQuality(Course_quality);
        outputData.setQualityDescription(Quality_description);
        List<Rule> ruleList =  new ArrayList();
        for (i = 1; i <= 4; i++) {
            n = 0;
            Rule rule = new Rule();
            rule.setType(a[i]);
            rule.setSatisfctionRate((100.0 - (Class_V[i] * 100.0) / (MNum*Weak_Day*1.0)) + "%");
            List<Unsatisfaction> unsatisfactionList = new ArrayList();
            for (j = 1; j <= DY_Class[i][0]; j++)//对应几条小规则
            {
                for (k = 1; k <= MNum; k++)
                {
                    if(Class_Rst[k][DY_Class[i][j]] == 1)//一旦触发
                    {
                        Unsatisfaction unsatisfaction = new Unsatisfaction();
                        unsatisfaction.setClassID(Class.get(MCls[k] - 1));
                        unsatisfaction.setCourseID(CourseID.get(MCrs[k] - 1));
                        p = 0;
                        List<Integer> dayLists = new ArrayList();
                        for (y = 1; y <= Weak_Day; y++)
                        {
                            if(Class_Weak[k][y][DY_Class[i][j]]==1)//哪一天
                            {
                                dayLists.add(y);
                                p++;
                                if(p == Class_Weak[k][0][DY_Class[i][j]])
                                    break;
                            }
                        }
                        unsatisfaction.setDayLists(dayLists);
                        unsatisfaction.setRule(c[DY_Class[i][j]]);
                        unsatisfactionList.add(unsatisfaction);
                        n++;
                        if(n == Class_V[i])
                            break;
                    }
                }
            }
            rule.setUnsatisfactions(unsatisfactionList);
            List<ProblematicCrs> problematicCrsList = new ArrayList();
            for (j = 1; j <= DY_Class[i][0]; j++)
            {
                for (x = 1; x <= Class_Num; x++)
                {
                    for (y = 1; y <= Weak_Day; y++)
                    {
                        for (z = 1; z <= Day_Crs; z++)
                        {
                            if (Class_Crs_Rst[x][y][z] == DY_Class[i][j])
                            {
                                ProblematicCrs problematicCrs = new ProblematicCrs();
                                problematicCrs.setType("classRule");
                                problematicCrs.setClassID(Class.get(x-1));
                                problematicCrs.setDay(y);
                                problematicCrs.setCrs(z);
                                problematicCrsList.add(problematicCrs);
                            }
                        }
                    }
                }
            }
            rule.setProblematicCrsList(problematicCrsList);
            ruleList.add(rule);
        }

        for (i = 1; i <= 3; i++) {
            n = 0;
            Rule rule = new Rule();
            rule.setType(b[i]);
            rule.setSatisfctionRate((100.0 - (Tea_V[i] * 100.0) / (Tea_Num * Weak_Day * 1.0)) + "%");
            List<Unsatisfaction> unsatisfactionList = new ArrayList();
            for (j = 1; j <= DY_Tea[i][0]; j++) {
                for (k = 1; k <= Tea_Num; k++) {
                    if (Tea_Rst[k][DY_Tea[i][j]] == 1) {
                        //System.out.print("进入了");
                        Unsatisfaction unsatisfaction = new Unsatisfaction();
                        unsatisfaction.setTeaName(Tea_Name.get(k - 1));
                        p = 0;
                        List<Integer> dayLists = new ArrayList();
                        for (y = 1; y <= Weak_Day; y++) {
                            if (Tea_Weak[k][y][DY_Tea[i][j]] == 1) {
                                dayLists.add(y);
                                p++;
                                if (p == Tea_Weak[k][0][DY_Tea[i][j]])
                                    break;
                            }
                        }
                        unsatisfaction.setDayLists(dayLists);
                        unsatisfaction.setRule(d[DY_Tea[i][j]]);
                        unsatisfactionList.add(unsatisfaction);
                        n++;
                        if (n == Tea_V[i])
                            break;
                    }
                }
            }
            rule.setUnsatisfactions(unsatisfactionList);
            List<ProblematicCrs> problematicCrsList = new ArrayList();
            for (j = 1; j <= DY_Tea[i][0]; j++)
            {
                for (x = 1; x <= Tea_Num; x++)
                {
                    for (y = 1; y <= Weak_Day; y++)
                    {
                        for (z = 1; z <= Day_Crs; z++)
                        {
                            if (Tea_Crs_Rst[x][y][z] == DY_Tea[i][j])
                            {
                                ProblematicCrs problematicCrs = new ProblematicCrs();
                                problematicCrs.setType("teaRule");
                                problematicCrs.setTeaName(Tea_Name.get(x-1));
                                problematicCrs.setDay(y);
                                problematicCrs.setCrs(z);
                                problematicCrsList.add(problematicCrs);
                            }

                        }
                    }
                }
            }
            rule.setProblematicCrsList(problematicCrsList);
            ruleList.add(rule);
        }
        outputData.setRules(ruleList);

        outputData.setWeakCrs(Weak_Day);
        List<ClassResultList> classResultLists = new ArrayList();
        for (i = 1; i <= Class_Num; i++) {
            for (j = 1; j <= Weak_Day; j++) {
                for (k = 1; k <= Day_Crs; k++) {
                    ClassResultList classResultList = new ClassResultList();
                    classResultList.setClassID(Class.get(i-1));
                    classResultList.setDay(j);
                    classResultList.setCrs(k);
                    if(Class_Crs[i][j][k]>0)
                    {
                        classResultList.setCourseID(CourseID.get(Class_Crs[i][j][k]-1));
                        if(Class_Tea[i][Class_Crs[i][j][k]] > 0)
                        {
                            classResultList.setTeaName(Tea_Name.get(Class_Tea[i][Class_Crs[i][j][k]]-1));
                        }
                    }
                    classResultLists.add(classResultList);
                }
            }
        }
        outputData.setClassResultList(classResultLists);

        /*
        for (i = 1; i <= Tea_Num; i++)
        {
            for (j = 1; j <= Weak_Day; j++)
            {
                for (k = 1; k <= Day_Crs; k++)
                    System.out.print(Tea_Crs[i][j][k]+" ");
                System.out.print("\n");
            }
            System.out.print("\n");
        }
        */
        List<TeaResultList> teaResultLists = new ArrayList();
        for (i = 1; i <= Tea_Num; i++) {
            for (j = 1; j <= Weak_Day; j++) {
                for (k = 1; k <= Day_Crs; k++) {
                    TeaResultList teaResultList = new TeaResultList();
                    teaResultList.setTeaName(Tea_Name.get(i-1));
                    //System.out.print("TeaName = " + Tea_Name.get(i-1));
                    teaResultList.setDay(j);
                    teaResultList.setCrs(k);
                    List<Integer> classIdLists = new ArrayList();
                    if(Tea_Crs[i][j][k] > 0 && Tea_Crs[i][j][k] < 100)
                    {
                        classIdLists.add(Class.get(Tea_Crs[i][j][k] - 1));
                        //System.out.print(Tea_Crs[i][j][k] + "\n");
                        //System.out.print(Tea_Crs[i][j][k] + " " + Class_Crs[Tea_Crs[i][j][k]][j][k] + " " +i +" "+ j +" "+ k +"\n");
                        teaResultList.setCourseID(CourseID.get(Class_Crs[Tea_Crs[i][j][k]][j][k]-1));
                    }
                    else if(Tea_Crs[i][j][k] >= 100)
                    {
                        Tea_Crs[i][j][k] = Tea_Crs[i][j][k] / 100;
                        for (x = 1; x <= Class_Num; x++)
                        {
                            if (Tea_Crs[i][j][k] == 0)
                            {
                                break;
                            }
                            else
                            {
                                y = Tea_Crs[i][j][k];
                                classIdLists.add(Class.get(Tea_Crs[i][j][k] % 100 - 1));
                                Tea_Crs[i][j][k] = Tea_Crs[i][j][k] / 100;
                            }
                        }
                        teaResultList.setCourseID(CourseID.get(Class_Crs[y][j][k] - 1));
                    }
                    teaResultList.setClassIdList(classIdLists);
                    teaResultLists.add(teaResultList);
                }
            }
        }
        outputData.setTeaResultList(teaResultLists);
        return outputData;
    }

}


