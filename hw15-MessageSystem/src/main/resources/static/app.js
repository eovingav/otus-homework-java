let stompClient = null;

const connect = () => {
    logout();
    stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));
    stompClient.connect({}, frame => {
        console.log(`Connected: ${frame}`);
        stompClient.subscribe('/topic/loginResult', loginResult =>
            showLogin(JSON.parse(loginResult.body)));
    });
};

const login = () => stompClient.send(
    "/app/login",
    {},
    JSON.stringify({
        'name': $("#name").val(),
        'password' : $("#password").val()
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
        $("#userLine").html("");
        $("#userLine").append('<tr>\n' +
            '        <td>John Doe</td>\n' +
            '        <td>25</td>\n' +
            '        <td>Kirova</td>\n' +
            '        <td>+79112221234</td>\n' +
            '    </tr>');
        $("#userLine").show();
    }else {
        logout();
    }
}

$(() => {
    $("form").on('submit', event => event.preventDefault());
    $("#login").click(login);
    $("#logout").click(logout);
});
