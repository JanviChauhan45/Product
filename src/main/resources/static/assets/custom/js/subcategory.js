$(window).on('load', function () {
      if (feather) {
        feather.replace({ width: 14, height: 14 });
      }
    })


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

var table;

function validateSubCategory(){
    let name = $('#subCategoryName').val().trim();
    let description = $('#description').val().trim();
    let categoryId = $('#categoryId').val();
    let regex = /^[A-Za-z0-9 ]+$/;

    if(name === ''){
        $.toast({
            heading:'Validation',
            text:'SubCategory Name is required',
            position:'top-right',
            icon:'error'
        });
        return false;
    }
    if(!regex.test(name)){
    $.toast({heading:'Validation',text:'SubCategory Name must be proper',position:'top-right',icon:'error'});
    }

    if(name.length < 3){
        $.toast({
            heading:'Validation',
            text:'SubCategory Name must be at least 3 characters',
            position:'top-right',
            icon:'error'
        });
        return false;
    }

    if(description === ''){
        $.toast({
            heading:'Validation',
            text:'Description is required',
            position:'top-right',
            icon:'error'
        });
        return false;
    }

    if(description.length < 10){
        $.toast({
            heading:'Validation',
            text:'Description must be at least 10 characters',
            position:'top-right',
            icon:'error'
        });
        return false;
    }

    if(!categoryId){
        $.toast({
            heading:'Validation',
            text:'Please select a Category',
            position:'top-right',
            icon:'error'
        });
        return false;
    }

    return true;
}

$(document).ready(function () {
    loadCategories();
    getSubCategories();
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

function saveSubCategory() {
     if(!validateSubCategory()){
            return;
        }

    var payload = {
        name: $('#subCategoryName').val().trim(),
        description: $('#description').val().trim(),
//        active: $('#active').is(':checked') ? 1 : 0,
        categoryId: $('#categoryId').val()
    };

    console.log(payload);

    $.ajax({
        url: "http://localhost:8080/subcategory/create",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(payload),

        success: function(response) {
            $.toast({
                heading: 'Success',
                text: 'SubCategory saved successfully',
                position: 'top-right',
                icon: 'success'
            });

            clearForm();
            $('.addmodal').modal('hide');
            getSubCategories();
        },

        error: function(xhr) {

            let message = 'Failed to save subcategory';

            $.toast({
                heading: 'Error',
                text: message,
                position: 'top-right',
                icon: 'error'
            });
        }
    });
}

function deleteSubCategory(id,btn) {

    $.ajax({

        url: "http://localhost:8080/subcategory/" + id,

        type: "DELETE",

        success: function(response) {

            console.log(response);

            $.toast({
                heading: 'Success',
                text: 'Deleted successfully',
                position: 'top-right',
                icon: 'success'
            });
            $(btn).closest('tr').remove();

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
function getSubCategories() {
     if ($.fn.DataTable.isDataTable('#subcategory_table')) {
            $('#subcategory_table').DataTable().destroy();
        }

    $.ajax({
        url: "http://localhost:8080/subcategory/all",
        type: "GET",
        success: function(response) {

            let tbody = $("#subcategoryTableBody");
            tbody.empty();


            response.forEach(function(sub) {
                var activeBadge = sub.active == 1
                                    ? '<span class="badge badge-success">Yes</span>'
                                    : '<span class="badge badge-danger">No</span>';

                tbody.append(`
                    <tr>
                        <td class="text-center">
                            <a onclick="editSubCategory(${sub.id},this)">
                                <i class="fas fa-edit text-success"></i>
                            </a>

                            <a
                            class=" delete_alert " onclick = "confirmDelete(${sub.id},this)"  data-toggle="tooltip" data-placement="bottom" data-original-title="Delete"
                                                              href="javascript:void(0);">
                                <i class="far fa-trash-alt text-danger"></i>
                            </a>
                        </td>
                        <td>${activeBadge}</td>
                        <td>${sub.id}</td>
                        <td>${sub.categoryName}</td>
                        <td>${sub.name}</td>
                        <td>${sub.description}</td>

                    </tr>
                `);
            });
             $('#subcategory_table').DataTable({
                            destroy: true,
                            pageLength: 10
                        });
        }
    });
}

function editSubCategory(id){
    $.ajax({
          url:"http://localhost:8080/subcategory/" + id,
          type: "GET",
          success: function(subcategory){
            $('#subCategoryId').val(subcategory.id);
            $('#subCategoryName').val(subcategory.name);
            $('#description').val(subcategory.description);
            $('#active').prop(
            'checked',subcategory.active == 1
            );
            $('#categoryId').val(subcategory.categoryId);
            $('#categoryId').selectpicker('refresh');
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
function updateSubCategory(id) {
    if(!validateSubCategory()){
            return;
        }
        console.log(
            "Checkbox checked:",
            $('#active').is(':checked')
        );

    var payload = {
        name: $('#subCategoryName').val().trim(),
        description: $('#description').val().trim(),
        active: $('#active').is(':checked') ? 1 : 0,
        categoryId: $('#categoryId').val()
    };

    $.ajax({
        url: "http://localhost:8080/subcategory/" + id,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(payload),
        success: function(response) {
            $.toast({
                heading: 'Success',
                text: 'SubCategory updated successfully',
                position: 'top-right',
                icon: 'success'
            });
            clearForm();
                $('.addmodal').modal('hide');
            getSubCategories();
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


function saveOrUpdateSubCategory(){
    let id = $('#subCategoryId').val();
   // console.log("Current ID:", id);
    if(id){
        updateSubCategory(id);
    }else{
        saveSubCategory();
    }
}



function clearForm(){
    $('#subCategoryId').val('');
    $('#subCategoryName').val('');
    $('#description').val('');
   $('#active').prop('checked',true);
    $('#categoryId').val('');
    $('#categoryId').selectpicker('refresh');
}

function openAddModal(){
    clearForm();
    $('.addmodal').modal('show');
}

function exportExcel() {

    let table = $('#subcategory_table').DataTable();

    if (table.rows({ search: 'applied' }).count() === 0) {

        $.toast({
            heading: 'Error',
            text: 'No records found for export',
            position: 'top-right',
            icon: 'error'
        });

        return;
    }

    let keyword = ($('.dataTables_filter input').val() || '').trim();

    let url = "http://localhost:8080/subcategory/excel";

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
                deleteSubCategory(id,btn)
            },
            cancel: function () {}
        }
    });
}
$('#subcategory_table').on('keyup', '.dataTables_filter input', function() {
    let keyword = $(this).val();
    if (keyword.trim() !== '') {
        searchSubCategory(keyword);
    } else {
        getSubCategories();
    }
});

function searchSubCategory(keyword) {
    if (keyword.trim() === '') {
        getSubCategories();
        return;
    }

    $.ajax({
        url: "http://localhost:8080/subcategory/search",
        type: "GET",
        data: { keyword: keyword },
        success: function(response) {

            if ($.fn.DataTable.isDataTable('#subcategory_table')) {
                $('#subcategory_table').DataTable().destroy();
            }

            let tbody = $('#subcategoryTableBody');
            tbody.empty();

            response.forEach(function(sub) {
                var activeBadge = sub.active == 1
                    ? '<span class="badge badge-success">Yes</span>'
                    : '<span class="badge badge-danger">No</span>';

                tbody.append(`
                    <tr>
                        <td class="text-center">
                            <a onclick="editSubCategory(${sub.id}, this)">
                                <i class="fas fa-edit text-success"></i>
                            </a>
                            <a class="delete_alert" onclick="confirmDelete(${sub.id}, this)"
                               href="javascript:void(0);">
                                <i class="far fa-trash-alt text-danger"></i>
                            </a>
                        </td>
                        <td>${activeBadge}</td>
                        <td>${sub.id}</td>
                        <td>${sub.categoryName}</td>
                        <td>${sub.name}</td>
                        <td>${sub.description}</td>
                    </tr>
                `);
            });

            // Re-initialize DataTable after populating
            $('#subcategory_table').DataTable({ pageLength: 10 });
        },
        error: function(xhr) {
            $.toast({
                heading: 'Error',
                text: 'Search failed',
                position: 'top-right',
                icon: 'error'
            });
        }
    });
}
