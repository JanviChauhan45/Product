function validateLogin(){

    let name = $('#name').val().trim();

    let password = $('#password').val().trim();

    if(name === ''){
        $.toast({
            heading:'Validation',
            text:'Username is required',
            position:'top-right',
            icon:'error'
        });
        return false;
    }

    if(password === ''){
        $.toast({
            heading:'Validation',
            text:'Password is required',
            position:'top-right',
            icon:'error'
        });
        return false;
    }
    return true;
}


function login() {
     if(!validateLogin()){
            return;
        }
    var name = $('#name').val().trim();

    var password = $('#password').val().trim();
    if(name === '') {

        $.toast({
            heading: 'Validation',
            text: 'Username is required',
            position: 'top-right',
            icon: 'error'
        });

        return;
    }
    if(name.length < 3) {
        $.toast({
            heading: 'Validation',
            text: 'Username must be at least 3 characters',
            position: 'top-right',
            icon: 'error'
        });
        return;
    }

    if(password === '') {

        $.toast({
            heading: 'Validation',
            text: 'Password is required',
            position: 'top-right',
            icon: 'error'
        });
        return;
    }

    if(password.length < 6) {
        $.toast({
            heading: 'Validation',
            text: 'Password must be at least 6 characters',
            position: 'top-right',
            icon: 'error'
        });
        return;
    }

    var payload = {
        name: name,
        password: password
    };
    console.log(payload);
    $.ajax({

        url: "/users/login",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(payload),
        success: function(response) {

            console.log(response);
            $.toast({
                heading: 'Success',
                text: 'Login successful',
                position: 'top-right',
                icon: 'success'
            });
            setTimeout(function () {
                window.location.href = '/product_master';
            }, 1500);
        },



         error: function(xhr) {

                    let message = "Something went wrong";

                    if(xhr.status === 400){
                            message = "Please check the entered details";
                        }

                        if(xhr.status === 401){
                            message = "Invalid username or password";
                        }

                        if(xhr.status === 404){
                            message = "Record not found";
                        }

                        if(xhr.status === 500){
                            message = "Server error occurred";
                        }

                    $.toast({
                        heading: 'Error',
                        text: message,
                        position: 'top-right',
                        icon: 'error'
                    });

                }
    });
}