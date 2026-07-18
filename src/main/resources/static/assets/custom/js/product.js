function parseDate(str) {

    let parts = str.split('/');

    return new Date(parts[2], parts[1] - 1, parts[0]);
}



$(window).on('load', function () {

$(document).on('input',"#price",function(){
        let price = $('#price').val().trim();

            if (!priceRegex.test(price)) {
                $.toast({
                    heading: 'Validation',
                    text: 'Price must contain numbers only',
                    position: 'top-right',
                    icon: 'error'
                });
                $('#price').focus();
                return false;
            }
            if (isNaN(price) || price <= 0) {
                $.toast({ heading: 'Validation', text: 'Price must be greater than 0', position: 'top-right', icon: 'error' });
                $('#price').focus();
                return false;
            }
    })
      if (feather) {
        feather.replace({ width: 14, height: 14 });
      }
    })


    $('#product_table').DataTable({
      destroy: true,
      scrollResize: true,
      scrollX: true,
      scrollY: 100,
      scrollCollapse: true,
      paging: true,
      lengthChange: false,
      scrollX: true,
      "bAutoWidth": true,
      paging: true,
      "bLengthChange": true,
       fixedColumns: true,
      "columnDefs": [{
        "targets": [0],
        "orderable": false,
         "width": "2%",
      }],
      "pageLength": 15,

      fixedColumns: {
        rightColumns: 0,
        leftColumns: 0
      },
      language: {
        paginate: {
          next: '<i class="fa fa-angle-double-right">',
          previous: '<i class="fa fa-angle-double-left">'
        }
      },
      dom:
        "<'row pl-0 pr-1'<'col-xl-9 col-lg-8 col-sm-8'pi><'col-xl-3 col-lg-4 col-sm-4'f>>" +
        "<'row pl-1 pr-1'<'col-xl-12 col-lg-12 col-sm-12'tr>>"

    });

    $('#start_date').closest('div').datepicker({
      autoclose: true,
      todayHighlight: true,
      format: "dd/mm/yyyy",
      clearBtn: true
    });
    $('#validfrom_date').closest('div').datepicker({
      autoclose: true,
      todayHighlight: true,
      format: "dd/mm/yyyy",
      clearBtn: true
    });
    $('#validto_date').closest('div').datepicker({
      autoclose: true,
      todayHighlight: true,
      format: "dd/mm/yyyy",
      clearBtn: true
    });


//console.log("JS FILE LOADED");
$(document).ready(function () {

//    console.log("Document loaded");

    loadCategories();

    getProducts();
});



function loadCategories() {

    $.ajax({
        url: "http://localhost:8080/category/all",
        type: "GET",

        success: function(response){

            $('#categoryId').empty();

            $('#categoryId').append('<option value="">Select Category</option>');

            response.forEach(function(category){

                if(category.active == 1){

                    $('#categoryId').append(
                        `<option value="${category.id}">
                            ${category.name}
                        </option>`
                    );

                }

            });

            $('#categoryId').selectpicker('refresh');

        }
    });

}





$('#categoryId').change(function () {

    var categoryId = $(this).val();

    console.log("Selected Category ID:", categoryId);

    loadSubCategories(categoryId);
});

function loadSubCategories(categoryId) {

    console.log("loadSubCategories() called with:", categoryId);

    $.ajax({
        url: "http://localhost:8080/subcategory/all",
        type: "GET",

        success: function(response) {



            $('#subCategoryId').empty();

            response.forEach(function(sub) {

                console.log("Checking subcategory:", sub);

                if(sub.categoryId == categoryId) {



                    $('#subCategoryId').append(
                        `<option value="${sub.id}">
                            ${sub.name}
                        </option>`
                    );
                }
            });



            $('#subCategoryId').selectpicker('refresh');


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

function validateProduct() {
    let today = new Date();
    today.setHours(0, 0, 0, 0); // strip time for clean comparison


    let name = $('#productName').val().trim();
    let regex = /^[A-Za-z0-9 ]+$/;
    if (name === '') {
        $.toast({ heading: 'Validation', text: 'Product Name is required', position: 'top-right', icon: 'error' });
        $('#productName').focus();
        return false;
    }
    if(name.length < 3){
        $.toast({heading: 'Name Length', text: 'Product Name is required', position: 'top-right', icon:'error'});
        $('#productName').focus();
        return false;
    }
    if(!regex.test(name)){
            $.toast({heading: 'Name',text:'Name must be characters only ', position: 'top-right', icon: 'error' });
            $('#name').focus();
            return false;
        }


    let description = $('#description').val().trim();

    if (description === '') {
        $.toast({ heading: 'Validation', text: 'Description is required', position: 'top-right', icon: 'error' });
        $('#description').focus();
        return false;
    }
    if(description.length < 10){
        $.toast({heading: 'Description should be of min 10 characters',position: 'top-right',icon: 'error'});
        $('#description').focus();
        return false;
    }
    if(!regex.test(description)){
                $.toast({heading: 'Description',text:'Description  must have characters,letters, numbers,space only ', position: 'top-right', icon: 'error' });
                $('#name').focus();
                return false;
            }


    if (!$('#categoryId').val()) {
        $.toast({ heading: 'Validation', text: 'Please select a Category', position: 'top-right', icon: 'warning' });
        $('#categoryId').focus();
        return false;
    }

     if (!$('#subCategoryId').val()) {
            $.toast({ heading: 'Validation', text: 'Please select a SubCategory', position: 'top-right', icon: 'warning' });
            $('#subCategoryId').focus();
            return false;
     }

    let price = parseFloat($('#price').val());
    let regexNum = /^[0-9]+$/;

    if(!regexNum.test(price)){
        $.toast({heading: 'Price',text:'Price must be in numbers only', position: 'top-right', icon: 'error' });
        $('#price').focus();
        return false;
    }
    if (isNaN(price) || price <= 0) {
        $.toast({ heading: 'Validation', text: 'Price must be greater than 0', position: 'top-right', icon: 'error' });
        $('#price').focus();
        return false;
    }

    if ($('#start_date').val() === '') {
        $.toast({ heading: 'Validation', text: 'Manufacturing Date is required', position: 'top-right', icon: 'error' });
        $('#start_date').focus();
        return false;
    }
    let manufactureDate = parseDate($('#start_date').val());
    if (manufactureDate > today) {
        $.toast({ heading: 'Validation', text: 'Manufacturing Date must be today or in the past', position: 'top-right', icon: 'error' });
        $('#start_date').focus();
        return false;
    }

    let serial = $('#serialno').val().trim();
            if (serial === '') {
                $.toast({ heading: 'Validation', text: 'Serial Number is required', position: 'top-right', icon: 'error' });
                $('#serialno').focus();
                return false;
            }

            if(serial.length < 3){
                $.toast({heading:'Validation',text: 'Serial Number should be more than 3 characters', position: 'top-right',icon:'error'});
            }
       let warranty = $('#warranty').val().trim();

     if (warranty === '') {
            $.toast({ heading: 'Validation', text: 'Warranty is required', position: 'top-right', icon: 'error' });
            $('#warranty').focus();
            return false;
        }
     if(warranty.length < 3){
           $.toast({heading: 'Warranty Length',text:'Warranty length should be more than 3 characters',position:'top-right',icon:'error'});
           $('#warranty').focus();
           return false;
     }
      if(!regex.test(warranty)){
             $.toast({heading: 'Warranty',text:'Warranty must be in characters,space, numbers only', position: 'top-right', icon: 'error' });
             $('#warranty').focus();
             return false;
         }


    if ($('input[name="option1"]:checked').length === 0) {
        $.toast({ heading: 'Validation', text: 'Select at least one color', position: 'top-right', icon: 'error' });
        return false;
    }

    if ($('input[name="optradio"]:checked').length === 0) {
        $.toast({ heading: 'Validation', text: 'Select Product Condition', position: 'top-right', icon: 'error' });
        return false;
    }

     let discount = parseFloat($('#discount').val() || 0);
        if (isNaN(discount) || discount < 0) {
            $.toast({ heading: 'Validation', text: 'Discount cannot be negative', position: 'top-right', icon: 'error' });
            $('#discount').focus();
            return false;
        }

        if (discount > price) {
            $.toast({ heading: 'Validation', text: 'Discount cannot exceed Price', position: 'top-right', icon: 'error' });
            $('#discount').focus();
            return false;
        }
         if(!regexNum.test(discount)){
                $.toast({heading: 'Discount',text:'Discount must be in numbers only', position: 'top-right', icon: 'error' });
                $('#discount').focus();
                return false;
            }



       if($('#validfrom_date').val()=== ''){
       $.toast({heading:'Valid From ',text: 'ValidFrom Date is required',position:'top-right',icon:'error'});
       $('#validfrom_date').focus();
       return false;
       }
        let validFrom = parseDate($('#validfrom_date').val());
               if (validFrom < today) {
                   $.toast({ heading: 'Validation', text: 'Valid From must be today or in the future', position: 'top-right', icon: 'error' });
                   $('#validfrom_date').focus();
                   return false;
               }



       if($('#validto_date').val() === ''){
       $.toast({heading:'Valid To' , text:'Valid To Date is required',position:'top-right',icon:'error'});
       $('#validto_date').focus();
       return false;
       }



        let validTo = parseDate($('#validto_date').val());
        if (validTo <= validFrom) {
            $.toast({ heading: 'Validation', text: 'Valid To must be after Valid From', position: 'top-right', icon: 'error' });
            $('#validto_date').focus();
            return false;
        }

       $('#productImage').change(function(){

           let file = this.files[0];

           if(!file) return;

           if(file.size > 10 * 1024 * 1024){

               $.toast({
                   heading:'Validation',
                   text:'Maximum file size is 10 MB',
                   position:'top-right',
                   icon:'warning'
               });

               $(this).val('');
           }

       });



    return true;
}



function saveProduct() {

    if(!validateProduct()){return; }

    var imageFile = $('#productImage')[0].files[0];


    var formData = new FormData();

    formData.append("name",            $('#productName').val().trim());
    formData.append("description",     $('#description').val().trim());
    formData.append("price",           $('#price').val());
    formData.append("discount",        $('#discount').val());
    formData.append("manufactureDate", $('#start_date').val());
    formData.append("validFrom",       $('#validfrom_date').val());
    formData.append("validTo",         $('#validto_date').val());
    formData.append("serialno",        $('#serialno').val());
    formData.append("warranty",        $('#warranty').val());
    formData.append("subCategoryId",   $('#subCategoryId').val());
    formData.append("active",          $('#active').is(':checked') ? 1 : 0);


    var selectedColors = [];
    $('input[name="option1"]:checked').each(function () {
        selectedColors.push($(this).next('label').text().toUpperCase());
    });
    formData.append("color", selectedColors.join(","));

    var condition = $('input[name=optradio]:checked').next('label').text().toUpperCase();
    formData.append("condition", condition);
    if (imageFile) {
        formData.append("image", imageFile);
    }



    $.ajax({
        url: "http://localhost:8080/product",
        type: "POST",
        data: formData,
        contentType: false,
        processData: false,
        success: function(response) {
            console.log("Saved:", response);
            $.toast({ heading: 'Success', text: 'Product saved', position: 'top-right', icon: 'success' });


            $('.addmodal').modal('hide');
            clearForm();

            getProducts();
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





function getProducts() {

    console.log("getProducts() called");
    if ($.fn.DataTable.isDataTable('#product_table')) {
            $('#product_table').DataTable().destroy();
        }

    $.ajax({
        url: "http://localhost:8080/product/getAll",
        type: "GET",

        success: function(response) {

            console.log("Products API Response:", response);

            $('#productTableBody').empty();

            response.forEach(function(product) {

                console.log("Appending product row:", product);

               var row = `
               <tr>
                  <td class="text-center">
                                <span data-toggle="modal" data-target=".addmodal">
                                  <a class=" " onclick="editProduct(${product.id})" data-toggle="tooltip" data-placement="bottom" data-original-title="Edit"
                                    href="javascript:void(0);">
                                    <i class="fas fa-edit m-r-5 text-success"></i>
                                  </a>
                                </span>

                                <a class=" delete_alert " onclick = "confirmDelete(${product.id},this)" data-toggle="tooltip" data-placement="bottom" data-original-title="Delete"
                                  href="javascript:void(0);">
                                  <i class="far fa-trash-alt  text-danger"></i>
                                </a>
                     </td>
                   <td>
                       ${
                           product.active == 1
                           ? '<span class="badge badge-success">Yes</span>'
                           : '<span class="badge badge-danger">No</span>'
                       }
                   </td>

                   <td>
                   <a href="javascript:void(0)" data-toggle="popover" data-trigger="hover" data-html="true"
                                   data-placement="right"
                                   data-template='<div class="popover fade bs-popover-right" role="tooltip" x-placement="right"><div class="arrow"></div><h3 class="popover-header p-0 border_radius6"></h3></div>'
                                   data-title="<img src='http://localhost:8080/uploads/${product.imageURL}' height='150' class='border_radius6'>">
                       ${
                           product.imageURL && product.imageURL !==
                           `<img
                                 src="/images/noimage.png"
                                 width="60"
                                 height="60"
                                 style="object-fit:cover;border-radius:5px;cursor:pointer;"
                             onerror="this.style.display='none'">`

                           ?
                           `<img
                               src="http://localhost:8080/uploads/${product.imageURL}"
                               width="60"
                               height="60"
                               style="object-fit:cover;border-radius:5px;cursor:pointer;"
                               onerror="this.style.display='none'">`
                           :
                           ''
                       }
                       </a>
                   </td>

                   <td>${product.id}</td>
                   <td>${product.name}</td>
                   <td>${product.description}</td>
                   <td>${product.categoryName}</td>
                   <td>${product.price}</td>
                   <td>${product.discount}%</td>

               </tr>
               `;

                $('#productTableBody').append(row);
            });

             $('#product_table').DataTable({
                            pageLength: 15,
                            searching: true,
                            ordering: true,
                            paging: true
                        });
            console.log("Products table updated");
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





function deleteProduct(id,btn) {

    console.log("deleteProduct() called with ID:", id);

    $.ajax({
        url: "http://localhost:8080/product/" + id,
        type: "DELETE",

        beforeSend: function() {

            console.log("Sending DELETE request...");
        },

        success: function(response) {

            console.log("Delete successful");
            console.log("Response:", response);

            $.toast({
                heading: 'Success',
                text: 'Deleted successfully',
                position: 'top-right',
                icon: 'success'
            });


            $(btn).closest('tr').remove();
            //getProducts();

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

function editProduct(id) {

    $.ajax({

        url: "http://localhost:8080/product/" + id,
        type: "GET",

        success: function(product) {

            $('#productId').val(product.id);

            $('#productName').val(product.name);
            $('#description').val(product.description);
            $('#price').val(product.price);
            $('#discount').val(product.discount);

            $('#serialno').val(product.serialno);
            $('#warranty').val(product.warranty);

            $('#active').prop(
                'checked',
                product.active == 1
            );
            $('#start_date').val(product.manufactureDate);

            $('#validfrom_date').val(product.validFrom);

            $('#validto_date').val(product.validTo);
          $('#existingProductImage').attr('src', 'http://localhost:8080/uploads/' + product.imageURL).show();
           $('#categoryId option').each(function () {

               if ($(this).text().trim() === product.categoryName) {

                   $('#categoryId').val($(this).val());
               }
           });

           $('#categoryId').selectpicker('refresh');

           loadSubCategories($('#categoryId').val());


           setTimeout(function () {

               $('#subCategoryId').val(product.subCategoryId);

               $('#subCategoryId').selectpicker('refresh');

           }, 500);

           $('input[name="optradio"]').prop('checked', false);

           if(product.condition){
               $('input[name="optradio"]').each(function(){

                   if(
                       $(this)
                       .next('label')
                       .text()
                       .toUpperCase()
                       === product.condition
                   ){
                       $(this).prop('checked', true);
                   }
               });
           }

           $('input[name="option1"]').prop('checked', false);

           if(product.color){

               product.color.forEach(function(c){

                   $('input[name="option1"]').each(function(){

                       if($(this)
                           .next('label')
                           .text()
                           .toUpperCase()
                           === c
                       ){
                           $(this).prop('checked', true);
                       }

                   });

               });

           }
           console.log("Image URL:", product.imageURL);
//           console.log("Colors =", selectedColors);
//           console.log("Image =", imageFile);



          $('.addmodal').modal('show');
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

function updateProduct(id){
 if(!validateProduct()){
        return;
    }

    let formData = new FormData();

    formData.append("name", $('#productName').val().trim());
    formData.append("description", $('#description').val().trim());
    formData.append("price", $('#price').val());
    formData.append("discount", $('#discount').val());

    formData.append("manufactureDate", $('#start_date').val());
    formData.append("validFrom", $('#validfrom_date').val());
    formData.append("validTo", $('#validto_date').val());

    formData.append("serialno", $('#serialno').val());
    formData.append("warranty", $('#warranty').val());

    formData.append("subCategoryId",$('#subCategoryId').val());

    formData.append( "active",$('#active').is(':checked') ? 1 : 0
    );

    let selectedColors = [];

    $('input[name="option1"]:checked').each(function () {

        selectedColors.push(
            $(this).next('label')
                   .text()
                   .toUpperCase()
        );
    });

    formData.append(
        "color",
        selectedColors.join(",")
    );

    let condition =
        $('input[name="optradio"]:checked')
            .next('label')
            .text()
            .toUpperCase();

    formData.append("condition", condition);

    let imageFile =
        $('#productImage')[0].files[0];

    if(imageFile){
        formData.append("image", imageFile);
    }
    $('#productImage').change(function(){

        let file = this.files[0];

        if(!file) return;

        if(file.size > 10 * 1024 * 1024){

            $.toast({
                heading:'Validation',
                text:'Maximum file size is 10 MB',
                position:'top-right',
                icon:'warning'
            });

            $(this).val('');
        }

    });


    $.ajax({

        url:
        "http://localhost:8080/product/" + id,
        type: "PUT",

        data: formData,

        contentType: false,

        processData: false,

        success: function(response){

            $.toast({
                heading: 'Success',
                text: 'Product updated successfully',
                position: 'top-right',
                icon: 'success'
            });



            $('.addmodal').modal('hide');
            clearForm();

            getProducts();
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

function openAddModal(){
    clearForm();
    $('.addmodal').modal('show');
}

function clearForm(){
    $('#productId').val('');
    $('#productName').val('');
    $('#description').val('');
    $('#active').val('');
    $('#categoryId').val('');
    $('#categoryId').selectpicker('refresh');
    $('#subcategoryId').val('');
    $('#subcategoryId').selectpicker('refresh');
    $('#price').val('');
    $('#start_date').val('');
    $('#validfrom_date').val('');
    $('#validto_date').val('');
    $('#serialno').val('');
    $('#warranty').val('');
    $('#productImage').val('');
    $('#discount').val('');
   $('.fileinput-filename').text('');
  $('#existingProductImage').attr('src','').hide();
    $('#active').prop('checked',true);

}

function saveOrUpdateProduct(){
    let id = $('#productId').val();

    if(id){
        updateProduct(id);
    }
    else{
        saveProduct();
    }

}

function exportExcel() {

    let table = $('#product_table').DataTable();
    let keyword = ($('.dataTables_filter input').val() || '').trim();

    if (table.rows({ search: 'applied' }).count() === 0) {

        $.toast({
            heading: 'Export',
            text: 'No records available to export',
            position: 'top-right',
            icon: 'warning'
        });

        return;
    }

    let url = "http://localhost:8080/product/excel";

    if (keyword !== "") {
        url += "?keyword=" + encodeURIComponent(keyword);
    }

    window.location.href = url;
}

function confirmDelete(id, btn) {
    $.confirm({
        title: 'Confirm Delete',
        content: 'Are you sure?',
        buttons: {
            delete: function () {
                deleteProduct(id, btn);
            },
            cancel: function () {}
        }
    });
}

