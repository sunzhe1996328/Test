package QZB;

/**
 * @program: PK
 * @description: 班级课程
 * @author: Mr.Sun
 * @create: 2018-09-25 22:16
 **/
public class CourseInfo {
    /**
     *课程ID
     */
    private int courseID;
    /**
     *课程的总课时数
     */
    private int courseNum;
    /**
     *课程连堂课的次数
     */
    private int courseLTNum;
    /**
     *课程的授课教师，只有两种可能，0或者是1，超过1数据异常
     */
    private int courseTeaNum;
    /**
     *教师ID，若无老师则不填
     */
    private int courseTeaID;
    /**
     * 课程统一走班的时序，有0,1,2,3
     */
    private int courseTime;
    /**
     * 课程类型0表示普通课，1表示走班课
     */
    private int courseType;

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(int courseNum) {
        this.courseNum = courseNum;
    }

    public int getCourseLTNum() {
        return courseLTNum;
    }

    public void setCourseLTNum(int courseLTNum) {
        this.courseLTNum = courseLTNum;
    }

    public int getCourseTeaNum() {
        return courseTeaNum;
    }

    public void setCourseTeaNum(int courseTeaNum) {
        this.courseTeaNum = courseTeaNum;
    }

    public int getCourseTeaID() {
        return courseTeaID;
    }

    public void setCourseTeaID(int courseTeaID) {
        this.courseTeaID = courseTeaID;
    }

    public int getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(int courseTime) {
        this.courseTime = courseTime;
    }

    public int getCourseType() {
        return courseType;
    }

    public void setCourseType(int courseType) {
        this.courseType = courseType;
    }
}
