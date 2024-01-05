//1.获取用户名输入框
var usernameInput = document.getElementById("username")
console.log(usernameInput)
//2.绑定onblur事件，失去焦点
usernameInput.onblur = checkUsername;
function checkUsername(){
    //3.获取用户名
    const username = usernameInput.value.trim()
    console.log(username)
    //4.判断用户名;6-12位
    const reg = /^\w{5,6}$/
    const flag = reg.test(username)
    if (flag) {
        //符合规则
        document.getElementById("username_err").style.display="none"
    } else {
        document.getElementById("username_err").style.display=""
    }
    return flag;
}
//1.获取密码输入框
const passwordInput = document.getElementById("password")
//2.绑定onblur事件，失去焦点
passwordInput.onblur =checkPassword;
function checkPassword(){
    //3.获取用户名
    const password = passwordInput.value.trim()
    //4.判断用户名;6-12位
    const reg = /^\w{6,12}$/
    const flag = reg.test(password)
    if (flag) {
        //符合规则
        document.getElementById("password_err").style.display="none"

    } else {
        document.getElementById("password_err").style.display=""
    }
    return flag
}
//1.获取表单对象
const LoginForm = document.getElementsByClassName("_form")
//2.绑定onsubmit事件
LoginForm.onsubmit=function(){
    //挨个判断每一个表单是否符合要求，有一个不符合就阻止提交
    const flag = checkUsername()&&checkPassword()
    return flag;
}
