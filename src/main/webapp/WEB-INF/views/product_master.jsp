<!DOCTYPE html>

<html class="loading" lang="en" data-textdirection="ltr">
<!-- BEGIN: Head-->


<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, shrink-to-fit=no">
  <title>Emerging Five - User Master</title>
  <link rel="icon" type="image/x-icon" href="assets/custom/images/favicon.png" />


  <!-- BEGIN: Custom CSS-->
  <link rel="stylesheet" type="text/css" href="assets/custom/css/imports.css">

  <!-- END: Custom CSS-->
  <style>
    /* .popover { 
        pointer-events: none !important;
    } */
  </style>
</head>
<!-- END: Head-->

<!-- BEGIN: Body-->

<body class="horizontal-layout horizontal-menu  navbar-sticky footer-fixed  " data-open="hover"
  data-menu="horizontal-menu" data-col="">

  <div class="preloader"></div>

  <!-- BEGIN: Header-->
  <script src="assets/custom/js/header.js"></script>
  <script language="javascript" type="text/javascript">
    document.getElementById('product_master').className = "active";
  </script>
  <!-- END: Header-->




  <!-- BEGIN: Content-->
  <div class="app-content content ">
    <div class="content-overlay"></div>
    <div class="row pl-1 pr-1 p-b-5">
      <div class="col-xl-6 col-lg-6 col-sm-6 colmspadding padding0 width50">
        <div class="font-20">
          <span class="text-primary font-weight-700">Product Master</span>
        </div>
      </div>
      <div class="col-xl-6 col-lg-6 col-sm-6 colmspadding padding0 width50 text-right">
         <button type="button" class="btn btn-success m-r-5" onclick="exportExcel()">
             <i class="fas fa-file-excel m-r-5"></i>
                 Excel
          </button>

        <a class="btn btn-primary" onclick="openAddModal()">
          <i class="fas fa-plus m-r-5"></i>
          Add
        </a>
      </div>
    </div>
    <div class="datatable-design mb-0 pt-1 bg-white resize_wrapper">
      <table id="product_table" class="table table-striped table-custom-hover nowrap  display " style="width: 100%;">
        <thead>
          <tr>

            <th class="text-center" width="5%">Action</th>
            <th width="5%">Active</th>            
            <th width="5%" class="text-center">Image</th>
            <th>Product Id</th>
            <th>Product Name</th>
            <th width="30%">Description</th>
            <th>Category</th>
            <th class="text-right">Price</th>
            <th class="text-right">Discount</th>
          </tr>
        </thead>
        <tbody id="productTableBody">

        </tbody>
      </table>
    </div>


  </div>
  <!-- END: Content-->


  <div class="modal modal-right fade addmodal" data-keyboard="false" data-backdrop="static" tabindex="-1" role="dialog"
    aria-labelledby="" aria-hidden="true">
    <div class="modal-dialog modal-right-full-width  modal-dialog-scrollable" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <label class="modal-title " id="">Add/Edit Product Master</label>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body ">
          <div class="card m-b-5">
            <div class="header">
              <h6 class="mb-0">
                <span class="text-blue font-20">Basic </span><span class="font-20">Details</span>
              </h6>
            </div>
            <div class="card-body">
            <input type="hidden" id="productId">
              <div class="row">
                <div class="col-xl-4 col-lg-12 col-sm-12 mb-2">
                  <label class=" lable-align">Product Name</label>
                  <input type="text" id= "productName" class="form-control fieldmandatory" maxlength= "50" placeholder="Enter Product Name" oninput="this.value=this.value.replace(/[^a-zA-Z ]/g,'')">
                </div>
                <div class="col-xl-8 col-lg-12 col-sm-12 mb-2">
                  <label class="lable-align">Description</label>
                  <textarea id="description" class="form-control fieldmandatory textareasize"  maxlength="255" placeholder="Enter Description" oninput = "this.value=this.value.replace(/[^a-zA-Z0-9 ]/g,'')"></textarea>
                </div>
                <div class="col-xl-2 col-lg-12 col-sm-12 mb-2">
                  <label class="lable-align">Category</label>
                  <select  id="categoryId" class="selectpicker form-control typecombo" data-title="Select Category"
                    data-style=" bg-transfer fieldmandatory" data-live-search="true" data-size="5">
                    <option value="1">Electronics</option>
                    <option value="2">Men's Fashion</option>
                    <option value="3">Women's Fashion</option>
                  </select>
                </div>
                <div class="col-xl-2 col-lg-12 col-sm-12 mb-2">
                  <label class="lable-align">Sub Category</label>
                  <select  id="subCategoryId" class="selectpicker form-control typecombo" data-title="Select Sub Category"
                    data-style=" bg-transfer fieldmandatory" data-live-search="true" data-size="5">
                    <option value="1">TV</option>
                    <option value="2">Speakers</option>
                    <option value="3">Washing Machine</option>
                  </select>
                </div>
                <div class="col-xl-2 col-lg-12 col-sm-12 mb-2">
                  <label class=" lable-align">Price</label>
                  <div class="input-group ">
                    <div class="input-group-prepend ">
                        <span class="input-group-text fieldmandatory"><i
                                class="fas fa-rupee-sign"></i></span>
                    </div>
                    <input type="text" id="price" class="form-control" placeholder="Enter Price" maxlength="7" oninput="this.value=this.value.replace(/[^0-9]/g,'')" >
                </div>                  
                </div>
                <div class="col-xl-2 col-lg-12 col-sm-12 mb-2">
                  <label class=" lable-align">Manufacture  Date</label>
                  <div class="input-group date">
                    <input type="text"  class="form-control fieldmandatory" id="start_date" placeholder="Select Date" readonly>
                    <span class="input-group-addon inputgroups">
                        <i class="far fa-calendar-alt"></i>
                    </span>
                </div>             
                </div>
                <div class="col-xl-4 col-lg-12 col-sm-12 mb-2">
                  <label class=" lable-align">Product Serial No.</label>
                  <input id="serialno" type="text" class="form-control fieldmandatory" placeholder="Enter Product Serial No." maxlength="10" oninput="this.value=this.value.replace(/[^A-Za-z0-9 ]+$/)">
                </div>
                <div class="col-xl-4 col-lg-12 col-sm-12 mb-2">
                  <label class="lable-align">Warranty & Support</label>
                  <textarea id="warranty" class="form-control fieldmandatory textareasize" maxlength="100" placeholder="Enter Warranty & Support" oninput = "this.value=this.value.replace(/[^a-zA-Z0-9 ]/g,'')">></textarea>
                </div>

                <div class="col-xl-4 col-lg-12 col-sm-12 mb-2">
                  <label class="lable-align">Image Upload</label>
                  <div class="form-group mb-0 width300">
                    <div class="fileinput fileinput-new input-groups" data-provides="fileinput">
                        <div class="form-control fieldmandatory" data-trigger="fileinput">
                            <i class="fa fa-file fileinput-exists mr-1 m-t-4" aria-hidden="true"></i> <span class="fileinput-filename"></span>
                        </div>


                        <span class="input-groups-addon fileupload btn btn-success btn-anim btn-file waves-effect waves-float waves-light"><i class="fa fa-upload"></i> <span class="fileinput-new btn-text">Select
            file</span> <span class="fileinput-exists btn-text">Change</span>

                        <input type="file" name="..." id= "productImage" accept=".jpg,.jpeg,.png,.webp" >
                        </span>

                        <a href="javascript:void(0)" class="input-groups-addon btn btn-secondary btn-anim fileinput-exists waves-effect waves-float waves-light" data-dismiss="fileinput"><i class="fa fa-trash"></i><span class="btn-text">
            Remove</span></a>

                    </div>
                    <img id="existingProductImage" src="" width="50" height="50"
                             style="object-fit:cover; border-radius:5px; border:1px solid #ddd;
                                    display:none; margin-left:8px;">
                </div>
                </div>
              
              </div>

              <div class="row">
                <div class="col-xl-4 col-lg-12 col-sm-12">
                  <label class=" lable-align">Product Condition</label>
                  <div class="d-flex">
                    <div class="form-check mr-1">
                      <input type="radio" class="form-check-input" id="radio1" name="optradio" value="option1">
                      <label class="form-check-label" for="radio1">Old</label>
                    </div>
                    <div class="form-check mr-1">
                      <input type="radio" class="form-check-input" id="radio2" name="optradio" value="option2">
                      <label class="form-check-label" for="radio2">New</label>
                    </div>   
                    <div class="form-check">
                      <input type="radio" class="form-check-input" id="radio3" name="optradio" value="option3">
                      <label class="form-check-label" for="radio3">Refurbished</label>
                    </div>   
                  </div>                             
                </div>
                <div class="col-xl-4 col-lg-12 col-sm-12">
                  <label class=" lable-align">Product Colour</label>
                  <div class="d-flex">                  
                  <div class="form-check mr-1">
                    <input class="form-check-input" type="checkbox" id="check1" name="option1" value="">
                    <label class="form-check-label" for="check1">Red</label>
                  </div>
                  <div class="form-check mr-1">
                    <input class="form-check-input" type="checkbox" id="check2" name="option1" value="">
                    <label class="form-check-label" for="check2">Black</label>
                  </div>
                  <div class="form-check mr-1">
                    <input class="form-check-input" type="checkbox" id="check3" name="option1" value="">
                    <label class="form-check-label" for="check3">Gold</label>
                  </div>
                  <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="check4" name="option1" value="">
                    <label class="form-check-label" for="check4">Blue</label>
                  </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="card">
            <div class="header">
              <h6 class="mb-0">
                <span class="text-blue font-20">Product </span><span class="font-20">Discount</span>
              </h6>
            </div>
            <div class="card-body">
              <div class="row">
                <div class="col-xl-2 col-lg-12 col-sm-12 mb-1">
                  <label class=" lable-align">Discount</label>
                  <input id="discount" type="text" class="form-control fieldmandatory"  maxlength="2" placeholder="Enter Discount" oninput="this.value=this.value.replace(/[^0-9]/g,'')">
                </div>                                                            
                <div class="col-xl-2 col-lg-12 col-sm-12 mb-1">
                  <label class=" lable-align">Valid From</label>
                  <div class="input-group date">
                    <input type="text" class="form-control fieldmandatory" id="validfrom_date" placeholder="Select Date" readonly>
                    <span class="input-group-addon inputgroups">
                        <i class="far fa-calendar-alt"></i>
                    </span>
                </div>             
                </div>
                <div class="col-xl-2 col-lg-12 col-sm-12 mb-1">
                  <label class=" lable-align">Valid To</label>
                  <div class="input-group date">
                    <input type="text" class="form-control fieldmandatory" id="validto_date" placeholder="Select Date" readonly>
                    <span class="input-group-addon inputgroups">
                        <i class="far fa-calendar-alt"></i>
                    </span>
                </div>             
                </div>                                           
              </div>
            </div>
          </div>


        </div>
        <div class="modal-footer">
          <div class="row">
            <div class="col-xl-6 col-lg-6 col-sm-6 text-right colmspadding width50">
              <div class="form-check float-left">
                <input type="checkbox" class="form-check-input " id="active" checked>
                <label class="form-check-label mb-0" for="active m-l-5">Active</label>
              </div>
            </div>
            <div class="col-xl-6 col-lg-6 col-sm-6 text-right colmspadding width50">
              <a href="javascript:void(0)" onclick="saveOrUpdateProduct()" class="btn btn-success  m-r-5"  id="toastr-success">
                <i class="far fa-save m-r-5"></i>
                Save
              </a>

              <a href="javascript:void(0)" class="btn btn-secondary " data-dismiss="modal">
                <i class="fas fa-times m-r-5 "></i>
                Close
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>


  <!-- BEGIN: Footer-->
  <script src="assets/custom/js/footer.js"></script>
  <!-- END: Footer-->


  <!-- BEGIN: Vendor JS-->
  <script src="assets/vendors/js/vendors.min.js"></script>
  <!-- BEGIN Vendor JS-->

  <!-- BEGIN: Page Vendor JS-->
  <script src="assets/vendors/js/ui/jquery.sticky.js"></script>


  <!-- END: Page Vendor JS-->

  <!-- BEGIN: Theme JS-->
  <script src="assets/js/core/app-menu.min.js"></script>
  <script src="assets/js/core/app.min.js"></script>
  <script src="assets/js/scripts/customizer.min.js"></script>
  <!-- END: Theme JS-->

  <!-- BEGIN: Page JS-->
  <script src="assets/custom/plugins/jquery-ui/jquery-ui.min.js"></script>
  <script src="assets/custom/plugins/datatables/js/datatables.min.js"></script>
  <script src="assets/custom/plugins/datatables/js/dataTables.responsive.min.js"></script>
  <script src="assets/custom/plugins/datatables/js/dataTables.colVis.js"></script>
  <script src="assets/custom/plugins/datatables/js/dataTables.scrollResize.js"></script>
  <script src="assets/custom/plugins/scrollable/js/scrollabar.min.js"></script>
  <script src="assets/custom/plugins/scrollable/js/scrollable.js"></script>
  <script src="assets/custom/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
  <script src="assets/custom/plugins/bootstrap-select/bootstrap-select.js"></script>
  <script src="assets/custom/plugins/jquery_confirm_v3/jquery-confirm.min.js"></script>
  <script src="assets/custom/plugins/jasny-bootstrap/dist/js/jasny-bootstrap.min.js"></script>
  <script src="assets/custom/plugins/img_cropper/js/cropper_min.js"></script>
  <script src="assets/custom/plugins/jquery_confirm_v3/jquery-confirm-custom.js"></script>
  <script src="assets/custom/plugins/jquery_toastr/jquery.toast.min.js"></script>
  <script src="https://cdn.datatables.net/buttons/2.4.2/js/dataTables.buttons.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.10.1/jszip.min.js"></script>
  <script src="https://cdn.datatables.net/buttons/2.4.2/js/buttons.html5.min.js"></script>
  <!-- END: Page JS-->
  <script src="assets/custom/js/custom.js"></script>

  <script>


    $(window).on('load', function () {
      if (feather) {
        feather.replace({ width: 14, height: 14 });
      }
    })


    $('#product_table').DataTable({
      destroy: true,
      scrollResize: true,
      scrollX: true,
      // scrollY: 100,
      scrollCollapse: true,
      paging: true,
      // lengthChange: false,
      // scrollX: true,
      "bAutoWidth": true,
      // paging: true,
      "bLengthChange": true,
      // fixedColumns: true,
      "columnDefs": [{
        "targets": [0],
        "orderable": false,
        // "width": "2%",
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
  </script>
  <script src="assets/custom/js/product.js"></script>
</body>
<!-- END: Body-->


</html>