$(function() {
  // Initialize form validation on the registration form.
  // It has the name attribute "registration"
  $("form[name='registration']").validate({
    // Specify validation rules
    rules: {
      // The key name on the left side is the name attribute
      // of an input field. Validation rules are defined
      // on the right side
      firstname: "required",
      lastname: "required",
      gender: "required",
      password: "required",
      email: {
        required: true,
        // Specify that email should be validated
        // by the built-in "email" rule
        email: true
      },
      confirmemail: {
        required: true,
        equalTo: "#email",
        email:true
      },
       mobile: {
        required: true,
        digits: true,
        rangelength: [10, 10]

      }
    },
    // Specify validation error messages
    messages: {
      firstname: "Please enter your firstname",
      lastname: "Please enter your lastname",
      gender: "Please select your gender",
      password:"Please enter your password",
      mobile: {
        required: "Please provide your mobile number",
        rangelength: "Please enter vaild 10 digits mobile number ",
        digits: "Please enter only digits"
      },
      email: "Please enter a valid email address",
      confirmemail :{
        required: "Please enter email address",
        equalTo: "Please enter same email as above email"
      }
    },
    // Make sure the form is submitted to the destination defined
    // in the "action" attribute of the form when valid
    submitHandler: function(form) {
      form.submit();
    }
  });
});