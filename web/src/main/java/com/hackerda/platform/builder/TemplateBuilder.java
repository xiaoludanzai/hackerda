package com.hackerda.platform.builder;

import com.hackerda.platform.domain.grade.GradeBO;
import com.hackerda.platform.domain.student.StudentUserBO;
import com.hackerda.platform.utils.SchoolTime;
import com.hackerda.platform.infrastructure.database.model.StudentUser;
import com.hackerda.platform.controller.vo.CourseTimeTableVo;
import com.hackerda.platform.utils.DateUtils;
import com.hackerda.platform.utils.SchoolTimeUtil;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Syaeldon
 * 模板消息
 */
@Service
public class TemplateBuilder {


    /**
     * 生成不带url跳转的模板消息
     * @param openid 用户的openid
     * @param list 模板消息的内容
     * @param templateId 模板id
     * @param miniProgram 小程序跳转
     * @return 模板消息
     */
    public WxMpTemplateMessage build(String openid, List<WxMpTemplateData> list, String templateId, WxMpTemplateMessage.MiniProgram miniProgram) {
        return build(openid, list, templateId, miniProgram, null);
    }



    /**
     *
     * @param openid 用户的openid
     * @param list 模板消息的内容
     * @param templateId 模板id
     * @param miniProgram 小程序跳转
     * @param url 跳转地址
     * @return 模板消息
     */
	public WxMpTemplateMessage build(String openid, List<WxMpTemplateData> list, String templateId,
                                     WxMpTemplateMessage.MiniProgram miniProgram, String url) {
		return WxMpTemplateMessage.builder()
				.toUser(openid)
				.templateId(templateId)
                .miniProgram(miniProgram)
				.data(list)
				.url(url)
				.build();
	}



    /**
     * 组装课程推送模板消息需要的WxMpTemplateData的列表
     * @param msg 课程推送信息
     * @return List<WxMpTemplateData>
     */

    public List<WxMpTemplateData> assemblyTemplateContentForCourse(StudentUser studentUser, CourseTimeTableVo courseTimeTableVo, SchoolTime schoolTime) {
        List<WxMpTemplateData> templateDataList = new ArrayList<>();
        //keyword1关键字
        WxMpTemplateData title = new WxMpTemplateData();
        title.setName("first");
        title.setValue(studentUser.getName()+"同学，你待会有课哟");
        String content = courseTimeTableVo.getCourse().getName() +
                " 第" + courseTimeTableVo.getClassOrder() + "节";
        WxMpTemplateData course = new WxMpTemplateData();
        course.setName("keyword1");
        course.setValue(content);
        //keyword2关键字
        WxMpTemplateData date = new WxMpTemplateData();
        date.setName("keyword2");
        date.setValue("第" + schoolTime.getSchoolWeek() + "周   " + schoolTime.getDayOfWeekChinese());
        //remark关键字
        WxMpTemplateData remark = getCourseRemark();
        templateDataList.add(title);
        templateDataList.add(course);
        templateDataList.add(date);
        templateDataList.add(remark);

        return templateDataList;
    }

    /**
     * 组装课程推送模板消息需要的WxMpTemplateData的列表
     * @param content 当天课表信息
     * @return List<WxMpTemplateData>
     */
    public List<WxMpTemplateData> assemblyTemplateContentForCourse(String content) {
        List<WxMpTemplateData> templateDataList = new ArrayList<>();
        WxMpTemplateData first = new WxMpTemplateData();
        first.setName("first");
        first.setValue("当日课表\n");
        WxMpTemplateData course = new WxMpTemplateData();
        course.setName("keyword1");
        course.setValue("\n" + content);
        WxMpTemplateData date = getCourseDateWithoutSpecificTime();
        WxMpTemplateData remark = getCourseRemark();
        templateDataList.add(first);
        templateDataList.add(course);
        templateDataList.add(date);
        templateDataList.add(remark);

        return templateDataList;
    }

    /**
     * 生成课程推送模板消息的remark
     * @return 课程推送模板消息的remark
     */
    private WxMpTemplateData getCourseRemark(){
        WxMpTemplateData remark = new WxMpTemplateData();
        remark.setName("remark");
        remark.setValue("开学快乐，大家一起加油～");
        return remark;
    }

    /**
     * 返回的日期只包含周数和天数
     * 如 13周 星期三
     * @return 包含日期的WxMpTemplateData
     */
    private WxMpTemplateData getCourseDateWithoutSpecificTime(){
        WxMpTemplateData date = new WxMpTemplateData();
        date.setName("keyword2");
        date.setValue("\n第" + SchoolTimeUtil.getSchoolWeek() + "周   " + SchoolTimeUtil.getDayOfWeekChinese());
        return date;
    }


    public List<WxMpTemplateData> gradeToTemplateData(StudentUserBO student, GradeBO grade){
        List<WxMpTemplateData> templateDataList = new ArrayList<>();

        WxMpTemplateData first = new WxMpTemplateData();
        first.setName("first");
        first.setValue(student.getName()+"同学！你的成绩更新啦");
        //keyword2关键字
        WxMpTemplateData key1 = new WxMpTemplateData();
        key1.setName("keyword1");
        key1.setValue(grade.getCourse().getName() + "\n更新时间:"+DateUtils.dateToChinese(grade.getOperateTime())+"\n");
        //remark关键字
        WxMpTemplateData key2 = new WxMpTemplateData();
        key2.setName("keyword2");
        key2.setValue(grade.getScore().toString()+ "  排名："+grade.getRank()+" 绩点: "+grade.getGradePoint().toString()+
                        "\n");

        WxMpTemplateData remark = new WxMpTemplateData();
        remark.setName("remark");
        remark.setValue("点击卡片即可查看详情\n如果觉得好用把我们介绍给你的朋友吧~比心");
        templateDataList.add(first);
        templateDataList.add(key1);
        templateDataList.add(key2);
        templateDataList.add(remark);
        return templateDataList;
    }
}
