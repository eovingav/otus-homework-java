let stompClient = null;

const connect = () => {
    logout();
    stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));
    stompClient.connect({}, frame => {
        console.log(`Connected: ${frame}`);
        stompClient.subscribe('/topic/loginResult', loginResult =>
            showLogin(JSON.parse(loginResult.body)));
        stompClient.subscribe('/topic/userList', userList =>
            showUsers(JSON.parse(userList.body)));
        stompClient.subscribe('/topic/userAdd', newUserID =>
            showAddUser(JSON.parse(newUserID.body)));
    });
};

const login = () => stompClient.send(
    "/app/login",
    {},
    JSON.stringify({
        'name': $("#name").val(),
        'password' : $("#password").val()
    }));

const userList = () => stompClient.send(
    "/app/getUsers",
    {},
    );

const userAdd = () => stompClient.send(
    "/app/userAdd",
    {},
    JSON.stringify({
        'name': $("#userAddName").val(),
        'password' : $("#userAddPassword").val(),
        'phonesString' : $("#userAddPhonesString").val(),
        'addressString' : $("#userAddAddressString").val()
    }));

const logout = () => {
    $("#loginForm").show();
    $("#users").hide();
    $("#userLine").hide();
}

const showLogin = loginResult => {
    if (loginResult.success){
        $("#loginForm").hide();
        $("#loginText").html("Вы вошли как ");
        $("#loginText").append(loginResult.name);
        $("#users").show();
        $("#userLine").show();
        userList();
    }else {
        logout();
    }
}

const showUsers = users => {
    $("#userLine").html("");
    users.forEach(function (user) {
        $("#userLine").append('<tr>\n' +
            '        <td>' + user.name + '</td>\n' +
            '        <td>' + user.age + '</td>\n' +
            '        <td>' + user.addressString + '</td>\n' +
            '        <td>' + user.phonesString + '</td>\n' +
            '    </tr>');
    });
}

const showAddUser = newUserID => {
    userList();
}

$(() => {
    $("form").on('submit', event => event.preventDefault());
    $("#login").click(login);
    $("#logout").click(logout);
    $("#userAdd").click(userAdd);
});
