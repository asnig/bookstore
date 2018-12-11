package cn.itcast.bookstore.user.web.servlet;

import cn.itcast.bookstore.user.domain.User;
import cn.itcast.bookstore.user.service.UserException;
import cn.itcast.bookstore.user.service.UserService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.itcast.servlet.BaseServlet;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@WebServlet(name = "UserServlet", urlPatterns = "/UserServlet")
public class UserServlet extends BaseServlet {
    private UserService userService = new UserService();

    /**
     * 激活功能
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        try {
            userService.active(code);
            request.setAttribute("msg", "恭喜你激活成功，可以马上登录了！");
        } catch (UserException e) {
            request.setAttribute("msg", e.getMessage());
        }
        return "f:/jsps/msg.jsp";
    }

    /**
     * 注册功能
     *
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */

    public String regist(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        User form = CommonUtils.toBean(req.getParameterMap(), User.class);
        form.setUid(CommonUtils.uuid());
        form.setCode(CommonUtils.uuid() + CommonUtils.uuid());

        //输入校验
        Map<String, String> errors = new HashMap<>();

        String username = form.getUsername();
        if (username == null || username.trim().isEmpty()) {
            errors.put("username", "用户名不能为空！");
        } else if (username.length() < 3 || username.length() > 10) {
            errors.put("username", "用户名长度必须在3~10之间！");
        }

        String password = form.getPassword();
        if (password == null || password.trim().isEmpty()) {
            errors.put("password", "密码不能为空！");
        } else if (password.length() < 3 || password.length() > 10) {
            errors.put("password", "密码长度必须在3~10之间！");
        }

        String email = form.getEmail();
        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "用户名不能为空！");
        } else if (!email.matches("\\w+@\\w+\\.\\w+")) {
            errors.put("email", "非法格式邮箱！");
        }

        if (errors.size() > 0) {
            req.setAttribute("errors", errors);
            req.setAttribute("form", form);
            return "f:/jsps/user/regist.jsp";
        }


        try {
            userService.regist(form);
        } catch (UserException e) {
            req.setAttribute("msg", e.getMessage());
            req.setAttribute("form", form);
            return "f:/jsps/user/regist.jsp";
        }

        //发邮件
        //准备配置文件
        req.setAttribute("msg", "恭喜！注册成功！请到邮箱激活");
        Properties props = new Properties();
        props.load(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"), "UTF-8"));
        String host = props.getProperty("host");//获取服务器主机
        String uname = props.getProperty("uname");//获取用户名
        String pwd = props.getProperty("pwd");//获取密码
        String from = props.getProperty("from");//获取发件人
        String to = form.getEmail();//获取发件人
        String subject = props.getProperty("subject");//获取主题
        String content = props.getProperty("content");//获取邮件内容
        content = MessageFormat.format(content, form.getCode());//替换占位符
        Session session = MailUtils.createSession(host, uname, pwd);
        Mail mail = new Mail(from, to, subject, content);
        try {
            MailUtils.send(session, mail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return "f:/jsps/msg.jsp";
    }

    /**
     * 登录功能
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User form = CommonUtils.toBean(request.getParameterMap(), User.class);
        try {
            User user = userService.login(form);
            request.getSession().setAttribute("session_user", user);
            return "r:/index.jsp";
        } catch (UserException e) {
            request.setAttribute("msg", e.getMessage());
            request.setAttribute("form", form);
            return "f:/jsps/user/login.jsp";
        }


    }


    /**
     * 退出功能
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String quit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        return "r:/index.jsp";
    }
}
