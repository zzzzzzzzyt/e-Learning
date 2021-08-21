package com.atguigu.eduorder.service.impl;

import com.atguigu.commonvo.CourseOrderVo;
import com.atguigu.commonvo.UcenterMemberVo;
import com.atguigu.eduorder.client.EduClient;
import com.atguigu.eduorder.client.UcenterClient;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.mapper.OrderMapper;
import com.atguigu.eduorder.service.OrderService;
import com.atguigu.eduorder.utils.OrderNoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author zzyt
 * @since 2021-08-17
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {


    //远程调用为了实现
    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient ;

    @Override
    public String saveOrder(String id, String memberId)
    {
        //我们需要课程信息和成员信息
        CourseOrderVo courseInfoOrder = eduClient.getCourseInfoOrder(id);
        UcenterMemberVo memberInfoOrder = ucenterClient.getMemberInfoOrder(memberId);

        //创建订单
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());//设置订单号
        order.setCourseId(id);//设置课程id
        order.setCourseTitle(courseInfoOrder.getTitle()); //设置课程标题
        order.setCourseCover(courseInfoOrder.getCover()); //设置课程封面
        order.setTeacherName(courseInfoOrder.getTeacherName());   //设置老师姓名
        order.setTotalFee(courseInfoOrder.getPrice()); //设置价格
        order.setMemberId(memberId); //设置成员id
        order.setEmail(memberInfoOrder.getEmail()); //设置成员邮箱号
        order.setNickname(memberInfoOrder.getNickname()); //设置成员名称
        order.setStatus(0); //设置订单的支付状态 0为未支付
        order.setPayType(1); //设置订单的支付类型 1为微信支付
        //加入数据库
        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
