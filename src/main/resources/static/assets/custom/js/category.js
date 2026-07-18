

$(window).on('load', function () {
      if (feather) {
        feather.replace({ width: 14, height: 14 });
      }
    })

    $('#category_table').DataTable({
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
function saveCategory() {

    let name = $('#name').val().trim();
    let description = $('#description').val().trim();
    let regex = /^[A-Za-z0-9 ]+$/;


    if(name === ''){
        $.toast({heading:'Validation',text:'Category Name is required',position:'top-right',icon:'error'});
        return;
    }

    if(!regex.test(name)){
        $.toast({heading:'Name', text:'Category Name should only have characters and numbers',position: 'top-right',icon:'error'});
        return;
    }
    if(description === ''){
    $.toast({heading:'Validation',text:'Description is required',position:'top-right', icon:'error'});
        return;
    }
    if(!regex.test(description)){
           $.toast({heading:'Validation',text:'Desription must be characters and numbers ',position:'top-right',icon:'error'});
           return;
        }
    if(description.length > 255){
        $.toast({heading:'Validation',text:'Description cannot exceed 255 characters',position:'top-right',icon:'error'});
        return;
    }
    if(description.length < 10){
        $.toast({heading:'Validation',text:'Description must be at least 10 characters',position:'top-right',icon:'error'});
        return;
    }


    var payload = {
        name: name,
        description: description,
        active: $('#active').is(':checked') ? 1 : 0
    };



    $.ajax({

        url: "http://localhost:8080/category/create",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(payload),

        success: function(response) {

            $.toast({
                heading: 'Success',
                text: 'Category saved successfully',
                position: 'top-right',
                icon: 'success'
            });
            clearForm();
            $('.addmodal').modal('hide');

            getCategories();
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
             message = "Category already exists ";
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



function getCategories() {


    if ($.fn.DataTable.isDataTable('#category_table')) {
        $('#category_table').DataTable().destroy();
    }

    $.ajax({
        url: "http://localhost:8080/category/all",
        type: "GET",

        success: function(response) {

            $('#categoryTableBody').empty();

            response.forEach(function(category) {

                var activeBadge = category.active == 1
                    ? '<span class="badge badge-success">Yes</span>'
                    : '<span class="badge badge-danger">No</span>';

                var row = `
                    <tr>
                        <td class="text-center">

                            <span data-toggle="modal" data-target=".addmodal">
                                <a onclick="editCategory(${category.id})"
                                   data-toggle="tooltip"
                                   data-placement="bottom"
                                   data-original-title="Edit"
                                   href="javascript:void(0);">

                                    <i class="fas fa-edit m-r-5 text-success"></i>
                                </a>
                            </span>

                            <a
                               class=" delete_alert "
                               onclick = "confirmDelete(${category.id},this)"
                               data-toggle="tooltip"
                               data-placement="bottom"
                               data-original-title="Delete"
                               href="javascript:void(0);" >

                                <i class="far fa-trash-alt text-danger"></i>
                            </a>

                        </td>

                        <td>${activeBadge}</td>
                        <td>${category.id}</td>
                        <td>${category.name}</td>
                        <td>${category.description}</td>
                    </tr>
                `;

                $('#categoryTableBody').append(row);
            });
            $('#category_table').DataTable({
                destroy: true,
                pageLength: 15
            });
        },

        error: function(xhr) {

            $.toast({
                heading: 'Error',
                text: xhr.responseText,
                position: 'top-right',
                icon: 'error'
            });

        }
    });
}
$(document).ready(function () {

    getCategories();
});

function editCategory(id){
     console.log("Editing id:", id);
    $.ajax({
        url: "http://localhost:8080/category/" + id,
        type: "GET",
        success: function(category){

            console.log("Response:", category);
            $("#activeDiv").show();


            $('#CategoryId').val(category.id);

            console.log("Stored ID:", $('#CategoryId').val());

            $('#name').val(category.name);
            $('#description').val(category.description);
            $('#active').prop('checked', category.active == 1);
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


function updateCategory(id) {

     let name = $('#name').val().trim();
     let description = $('#description').val().trim();
    if(name === '' || name.trim().length === 0){
            $.toast({
                heading:'Validation',
                text:'Category Name is required',
                position:'top-right',
                icon:'error'
            });
            return;
        }

        if(name.length < 3){
            $.toast({
                heading:'Validation',
                text:'Category Name must be at least 3 characters',
                position:'top-right',
                icon:'error'
            });
            return;
        }
        if(description === ''){
            $.toast({
                heading:'Validation',
                text:'Description is required',
                position:'top-right',
                icon:'error'
            });
            return;
        }
        if(description.length > 255){
            $.toast({
                heading:'Validation',
                text:'Description cannot exceed 255 characters',
                position:'top-right',
                icon:'error'
            });
            return;
        }
        if(description.length < 10){
            $.toast({
                heading:'Validation',
                text:'Description must be at least 10 characters',
                position:'top-right',
                icon:'error'
            });
            return;
        }

    var payload = {
        name: name,
        description: description,
        active: $('#active').is(':checked') ? 1 : 0
    };

    $.ajax({

        url: "http://localhost:8080/category/" + id,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(payload),
        success: function(response) {
            console.log(response);
            $.toast({
                heading: 'Success',
                text: 'Category updated successfully',
                position: 'top-right',
                icon: 'success'
            });
            clearForm();
            $('.addmodal').modal('hide');

            getCategories();
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



function deleteCategory(id,btn) {

    $.ajax({

        url: "http://localhost:8080/category/" + id,
        type: "DELETE",
        success: function(response) {
            console.log(response);
            $.toast({
                heading: 'Success',
                text: 'Category deleted successfully',
                position: 'top-right',
                icon: 'success'
            });

            $(btn).closest('tr').remove();
        },

        error: function(xhr) {

            let message = "Something went wrong";

            if(xhr.responseText){
                message = xhr.responseText;
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

function clearForm(){
    $('#CategoryId').val('');
    $('#name').val('');
    $('#description').val('');
    $('#active').prop('checked',true);

}

function openAddModal(){
    clearForm();
     $("#activeDiv").hide();
    $('.addmodal').modal('show');
}

function saveOrUpdateCategory() {

    let id = $('#CategoryId').val();

    console.log("Hidden ID =", id);

    if(id && id !== ''){
        console.log("Calling UPDATE");
        updateCategory(id);
    } else {
        console.log("Calling SAVE");
        saveCategory();
    }
}

function exportExcel() {

    let table = $('#category_table').DataTable();

    if (table.rows({ search: 'applied' }).count() === 0) {

        $.toast({
            heading: 'Export',
            text: 'No records available to export',
            position: 'top-right',
            icon: 'error'
        });

        return;
    }

    let keyword = ($('.dataTables_filter input').val() || '').trim();
    let url = "http://localhost:8080/category/excel";
    if(keyword !== ''){
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
                deleteCategory(id, btn);
            },
            cancel: function () {}
        }
    });


}


