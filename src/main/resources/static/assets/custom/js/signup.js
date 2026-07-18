

function signup() {
        let name = $('#name').val().trim();
        let email = $('#email').val().trim();
        let password = $('#password').val();
        let confirmPassword = $('#confirmPassword').val();
        let emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;

        if(name === ''){
            $.toast({
                heading:'Validation',
                text:'Username is required',
                position:'top-right',
                icon:'error'
            });
            return false;
        }

        if(email === ''){
            $.toast({
                heading:'Validation',
                text:'Email is required',
                position:'top-right',
                icon:'error'
            });
            return false;
        }

        if(!emailRegex.test(email)){
            $.toast({
                heading:'Validation',
                text:'Please enter a valid email address',
                position:'top-right',
                icon:'error'
            });
            return false;
        }

        let passwordRegex =/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&]).{8,}$/;
        if(password === ''){
            $.toast({
                heading:'Validation',
                text:'Password is required',
                position:'top-right',
                icon:'error'
            });
            return false;
        }



        if(password.length < 8){
            $.toast({
                heading:'Validation',
                text:'Password must be at least 8 characters',
                position:'top-right',
                icon:'error'
            });
            return false;
        }

        if(!passwordRegex.test(password)){
            $.toast({
                heading:'Validation',
                text:'Password must contain uppercase, lowercase, number and special character',
                position:'top-right',
                icon:'error'
            });
            return false;
        }

        if(confirmPassword === ''){
            $.toast({
                heading:'Validation',
                text:'Confirm Password is required',
                position:'top-right',
                icon:'error'
            });
            return false;
        }
        if(password !== confirmPassword){
            $.toast({
                heading:'Validation',
                text:'Passwords do not match',
                position:'top-right',
                icon:'error'
            });
            return false;
        }

    var payload = {
        name: $('#name').val().trim(),
        email: $('#email').val().trim(),
        password: password,
        confirmPassword: confirmPassword
    };
   // console.log(payload);


    $.ajax({
        url: "/users/create",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(payload),
        success: function(response) {
            console.log(response);
            $.toast({
                heading: 'Success',
                text: 'Registration successful',
                position: 'top-right',
                icon: 'success'
            });
            setTimeout(function () {
                window.location.href = '/';
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

