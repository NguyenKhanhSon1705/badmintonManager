/**
* Template Name: NiceAdmin - v2.4.1
* Template URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
* Author: BootstrapMade.com
* License: https://bootstrapmade.com/license/
*/
(function() {
  "use strict";

  /**
   * Easy selector helper function
   */
  const select = (el, all = false) => {
    el = el.trim()
    if (all) {
      return [...document.querySelectorAll(el)]
    } else {
      return document.querySelector(el)
    }
  }

  /**
   * Easy event listener function
   */
  const on = (type, el, listener, all = false) => {
    if (all) {
      select(el, all).forEach(e => e.addEventListener(type, listener))
    } else {
      select(el, all).addEventListener(type, listener)
    }
  }

  /**
   * Easy on scroll event listener 
   */
  const onscroll = (el, listener) => {
    el.addEventListener('scroll', listener)
  }

  /**
   * Sidebar toggle
   */
  if (select('.toggle-sidebar-btn')) {
    on('click', '.toggle-sidebar-btn', function(e) {
      select('body').classList.toggle('toggle-sidebar')
    })
  }

  /**
   * Search bar toggle
   */
  if (select('.search-bar-toggle')) {
    on('click', '.search-bar-toggle', function(e) {
      select('.search-bar').classList.toggle('search-bar-show')
    })
  }

  /**
   * Navbar links active state on scroll
   */
  let navbarlinks = select('#navbar .scrollto', true)
  const navbarlinksActive = () => {
    let position = window.scrollY + 200
    navbarlinks.forEach(navbarlink => {
      if (!navbarlink.hash) return
      let section = select(navbarlink.hash)
      if (!section) return
      if (position >= section.offsetTop && position <= (section.offsetTop + section.offsetHeight)) {
        navbarlink.classList.add('active')
      } else {
        navbarlink.classList.remove('active')
      }
    })
  }
  window.addEventListener('load', navbarlinksActive)
  onscroll(document, navbarlinksActive)

  /**
   * Toggle .header-scrolled class to #header when page is scrolled
   */
  let selectHeader = select('#header')
  if (selectHeader) {
    const headerScrolled = () => {
      if (window.scrollY > 100) {
        selectHeader.classList.add('header-scrolled')
      } else {
        selectHeader.classList.remove('header-scrolled')
      }
    }
    window.addEventListener('load', headerScrolled)
    onscroll(document, headerScrolled)
  }

  /**
   * Back to top button
   */
  let backtotop = select('.back-to-top')
  if (backtotop) {
    const toggleBacktotop = () => {
      if (window.scrollY > 100) {
        backtotop.classList.add('active')
      } else {
        backtotop.classList.remove('active')
      }
    }
    window.addEventListener('load', toggleBacktotop)
    onscroll(document, toggleBacktotop)
  }

  /**
   * Initiate tooltips
   */
  var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
  var tooltipList = tooltipTriggerList.map(function(tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl)
  })

  /**
   * Initiate quill editors
   */
  if (select('.quill-editor-default')) {
    new Quill('.quill-editor-default', {
      theme: 'snow'
    });
  }

  if (select('.quill-editor-bubble')) {
    new Quill('.quill-editor-bubble', {
      theme: 'bubble'
    });
  }

  if (select('.quill-editor-full')) {
    new Quill(".quill-editor-full", {
      modules: {
        toolbar: [
          [{
            font: []
          }, {
            size: []
          }],
          ["bold", "italic", "underline", "strike"],
          [{
              color: []
            },
            {
              background: []
            }
          ],
          [{
              script: "super"
            },
            {
              script: "sub"
            }
          ],
          [{
              list: "ordered"
            },
            {
              list: "bullet"
            },
            {
              indent: "-1"
            },
            {
              indent: "+1"
            }
          ],
          ["direction", {
            align: []
          }],
          ["link", "image", "video"],
          ["clean"]
        ]
      },
      theme: "snow"
    });
  }

  /**
   * Initiate TinyMCE Editor
   */
  const useDarkMode = window.matchMedia('(prefers-color-scheme: dark)').matches;
  const isSmallScreen = window.matchMedia('(max-width: 1023.5px)').matches;

  tinymce.init({
    selector: 'textarea.tinymce-editor',
    plugins: 'preview importcss searchreplace autolink autosave save directionality code visualblocks visualchars fullscreen image link media template codesample table charmap pagebreak nonbreaking anchor insertdatetime advlist lists wordcount help charmap quickbars emoticons',
    editimage_cors_hosts: ['picsum.photos'],
    menubar: 'file edit view insert format tools table help',
    toolbar: 'undo redo | bold italic underline strikethrough | fontfamily fontsize blocks | alignleft aligncenter alignright alignjustify | outdent indent |  numlist bullist | forecolor backcolor removeformat | pagebreak | charmap emoticons | fullscreen  preview save print | insertfile image media template link anchor codesample | ltr rtl',
    toolbar_sticky: true,
    toolbar_sticky_offset: isSmallScreen ? 102 : 108,
    autosave_ask_before_unload: true,
    autosave_interval: '30s',
    autosave_prefix: '{path}{query}-{id}-',
    autosave_restore_when_empty: false,
    autosave_retention: '2m',
    image_advtab: true,
    link_list: [{
        title: 'My page 1',
        value: 'https://www.tiny.cloud'
      },
      {
        title: 'My page 2',
        value: 'http://www.moxiecode.com'
      }
    ],
    image_list: [{
        title: 'My page 1',
        value: 'https://www.tiny.cloud'
      },
      {
        title: 'My page 2',
        value: 'http://www.moxiecode.com'
      }
    ],
    image_class_list: [{
        title: 'None',
        value: ''
      },
      {
        title: 'Some class',
        value: 'class-name'
      }
    ],
    importcss_append: true,
    file_picker_callback: (callback, value, meta) => {
      /* Provide file and text for the link dialog */
      if (meta.filetype === 'file') {
        callback('https://www.google.com/logos/google.jpg', {
          text: 'My text'
        });
      }

      /* Provide image and alt text for the image dialog */
      if (meta.filetype === 'image') {
        callback('https://www.google.com/logos/google.jpg', {
          alt: 'My alt text'
        });
      }

      /* Provide alternative source and posted for the media dialog */
      if (meta.filetype === 'media') {
        callback('movie.mp4', {
          source2: 'alt.ogg',
          poster: 'https://www.google.com/logos/google.jpg'
        });
      }
    },
    templates: [{
        title: 'New Table',
        description: 'creates a new table',
        content: '<div class="mceTmpl"><table width="98%%"  border="0" cellspacing="0" cellpadding="0"><tr><th scope="col"> </th><th scope="col"> </th></tr><tr><td> </td><td> </td></tr></table></div>'
      },
      {
        title: 'Starting my story',
        description: 'A cure for writers block',
        content: 'Once upon a time...'
      },
      {
        title: 'New list with dates',
        description: 'New List with dates',
        content: '<div class="mceTmpl"><span class="cdate">cdate</span><br><span class="mdate">mdate</span><h2>My List</h2><ul><li></li><li></li></ul></div>'
      }
    ],
    template_cdate_format: '[Date Created (CDATE): %m/%d/%Y : %H:%M:%S]',
    template_mdate_format: '[Date Modified (MDATE): %m/%d/%Y : %H:%M:%S]',
    height: 600,
    image_caption: true,
    quickbars_selection_toolbar: 'bold italic | quicklink h2 h3 blockquote quickimage quicktable',
    noneditable_class: 'mceNonEditable',
    toolbar_mode: 'sliding',
    contextmenu: 'link image table',
    skin: useDarkMode ? 'oxide-dark' : 'oxide',
    content_css: useDarkMode ? 'dark' : 'default',
    content_style: 'body { font-family:Helvetica,Arial,sans-serif; font-size:16px }'
  });

  /**
   * Initiate Bootstrap validation check
   */
  var needsValidation = document.querySelectorAll('.needs-validation')

  Array.prototype.slice.call(needsValidation)
    .forEach(function(form) {
      form.addEventListener('submit', function(event) {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        }

        form.classList.add('was-validated')
      }, false)
    })

  /**
   * Initiate Datatables
   */
  const datatables = select('.datatable', true)
  datatables.forEach(datatable => {
    new simpleDatatables.DataTable(datatable);
  })

  /**
   * Autoresize echart charts
   */
  const mainContainer = select('#main');
  if (mainContainer) {
    setTimeout(() => {
      new ResizeObserver(function() {
        select('.echart', true).forEach(getEchart => {
          echarts.getInstanceByDom(getEchart).resize();
        })
      }).observe(mainContainer);
    }, 200);
  }

})();

function selectCourt(element) {
    // Xóa đường viền màu xanh khỏi tất cả các sân
    const courts = document.querySelectorAll('.court-card');
    courts.forEach(court => court.classList.remove('border-primary'));

    // Thêm đường viền màu xanh vào sân đã chọn
    element.classList.add('border-primary');

    // Gán giá trị sân được chọn vào input ẩn
    const courtName = element.querySelector('p').innerText; // Lấy tên sân từ thẻ <p>
    document.getElementById('courtName').value = courtName;

    // Cập nhật tên sân vào phần "Tên sân" trong bảng "Phí thuê sân"
    const selectedCourtNameElement = document.getElementById('selectedCourtName');
    if (selectedCourtNameElement) {
        selectedCourtNameElement.querySelector('span').textContent = courtName;
    }

    // Gọi hàm cập nhật thời gian vào
    updateCheckInTime();
}


function addService() {
    // Lấy phần thân của bảng "Danh sách dịch vụ"
    const servicesTableBody = document.querySelector('#services-table tbody');

    // Lấy dòng mẫu từ bảng "Danh sách dịch vụ"
    const sampleRow = servicesTableBody.querySelector('tr');

    // Tạo một dòng mới từ dòng mẫu
    const newRow = sampleRow.cloneNode(true);

    // Xóa giá trị trong các ô input của dòng mới
    const quantityInput = newRow.querySelector('.quantity');
    const priceInput = newRow.querySelector('.price');
    const totalPriceElement = newRow.querySelector('.total-price');

    quantityInput.value = 0; // Đặt lại số lượng về 1
    priceInput.value = 0;    // Đặt lại đơn giá về 0
    totalPriceElement.textContent = '0'; // Đặt lại thành tiền về 0

    // Thêm dòng mới vào bảng "Danh sách dịch vụ"
    servicesTableBody.appendChild(newRow);

    // Cập nhật lại trạng thái nút xóa sau khi thêm dịch vụ
    updateDeleteButtonStatus();

    // Gọi hàm tính tổng sau khi thêm dịch vụ mới
    updateTotal();
}

    
    function updatePrice(selectElement) {
        // Lấy giá dịch vụ từ thuộc tính data-price của option được chọn
        const price = selectElement.options[selectElement.selectedIndex].getAttribute('data-price');
        
        // Lấy ô input "Đơn giá" trong cùng một dòng
        const row = selectElement.closest('tr');
        const priceInput = row.querySelector('.price');
        
        // Cập nhật giá vào ô input "Đơn giá"
        priceInput.value = price ? price : 0;
        
        // Gọi hàm tính tổng sau khi cập nhật đơn giá
        updateTotal();
    }
    
    function calculateTotal(element) {
        // Lấy dòng (tr) chứa ô input thay đổi
        const row = element.closest('tr');
        
        // Lấy giá trị của số lượng và đơn giá
        const quantity = parseFloat(row.querySelector('.quantity').value) || 0;
        const price = parseFloat(row.querySelector('.price').value) || 0;
        
        // Tính thành tiền
        const total = quantity * price;
        
        // Cập nhật giá trị vào cột "Thành tiền"
        const totalPriceElement = row.querySelector('.total-price');
        totalPriceElement.textContent = total.toFixed(3); // Hiển thị 3 chữ số sau dấu phẩy
        
        // Gọi hàm tính tổng sau khi tính thành tiền
        updateTotal();
    }

	function updateTotal() {
	    // Lấy giá trị phí thuê sân từ input hidden 'courtFee'
	    const courtFee = parseFloat(document.getElementById('courtFee').value) || 0;

	    // Lấy tất cả các giá trị thành tiền từ bảng dịch vụ
	    const totalPriceElements = document.querySelectorAll('.total-price');

	    // Tính tổng tiền từ các dịch vụ
	    let serviceTotal = 0;
	    totalPriceElements.forEach(element => {
	        serviceTotal += parseFloat(element.textContent) || 0;
	    });

	    // Tính tổng tiền cuối cùng (dịch vụ + phí thuê sân)
	    const grandTotal = serviceTotal + courtFee;

	    // Cập nhật tổng tiền vào phần tử hiển thị trên giao diện
	    const totalElement = document.querySelector('.total strong');
	    totalElement.textContent = grandTotal.toFixed(3) + ' VND';

	    // Cập nhật giá trị của input hidden 'totalAmount' để gửi lên server
	    const totalAmountInput = document.getElementById('totalAmount');
	    totalAmountInput.value = grandTotal.toFixed(3); // Lưu tổng tiền vào input hidden
	}


    
	function deleteService(button) {
	    // Lấy dòng (tr) chứa nút xóa
	    const row = button.closest('tr');
	    
	    // Xóa dòng khỏi bảng
	    row.remove();
	    
	    // Cập nhật lại trạng thái nút xóa sau khi xóa dịch vụ
	    updateDeleteButtonStatus();

	    // Gọi hàm tính tổng sau khi xóa dịch vụ
	    updateTotal();
	}
	
	function updateDeleteButtonStatus() {
	    // Lấy tất cả các dòng trong bảng
	    const rows = document.querySelectorAll('#services-table tbody tr');

	    // Nếu chỉ còn một dòng, vô hiệu hóa tất cả các nút xóa
	    if (rows.length <= 1) {
	        rows.forEach(row => {
	            const deleteButton = row.querySelector('.btn-danger');
	            deleteButton.disabled = true; // Vô hiệu hóa nút xóa
	        });
	    } else {
	        // Nếu có nhiều hơn một dòng, bật lại nút xóa
	        rows.forEach(row => {
	            const deleteButton = row.querySelector('.btn-danger');
	            deleteButton.disabled = false; // Bật lại nút xóa
	        });
	    }
	}
	
	// Lấy thời gian hiện tại và định dạng thành HH:mm:ss
	function updateCheckInTime() {
	    const selectedCourtName = document.getElementById('selectedCourtName').querySelector('span').textContent;

	    if (selectedCourtName !== "...") { // Chỉ cập nhật nếu đã chọn sân
	        const now = new Date();
	        const formattedTime = now.toLocaleTimeString('en-US', { hour12: false }); // Định dạng HH:mm:ss

	        // Hiển thị thời gian trên giao diện
	        document.getElementById('checkinDisplay').textContent = formattedTime;

	        // Lưu thời gian vào input ẩn
	        document.getElementById('checkinInput').value = formattedTime;
	    }
	}

	// Gọi hàm ngay khi trang được tải
	document.addEventListener('DOMContentLoaded', function () {
		updateDeleteButtonStatus();
	    document.getElementById('checkinDisplay').textContent = "...";
	    document.getElementById('checkinInput').value = ""; // Đặt giá trị rỗng ban đầu
	});
	
	
	document.getElementById('confirmPaymentBtn').addEventListener('click', function () {
	    const createdAt = document.querySelector('input[name="currentDateTime"]').value;
	    const courtName = document.getElementById('courtName').value;
	    const checkin = document.getElementById('checkinInput').value;
	    const checkout = new Date().toLocaleTimeString('en-US', { hour12: false });

	    const courtFee = parseFloat(document.getElementById('courtFee').value) || 0;

	    document.getElementById('modalCreatedAt').textContent = createdAt;
	    document.getElementById('modalCourtName').textContent = courtName;
	    document.getElementById('modalCheckin').textContent = checkin;
	    document.getElementById('modalCheckout').textContent = checkout;

	    // Tính tổng thời gian chơi
	    const checkinTime = new Date(`1970-01-01T${checkin}Z`);
	    const checkoutTime = new Date(`1970-01-01T${checkout}Z`);
	    let durationInSeconds = (checkoutTime - checkinTime) / 1000;

	    if (durationInSeconds < 0) {
	        durationInSeconds += 24 * 60 * 60; // Điều chỉnh nếu qua nửa đêm
	    }

	    const hours = Math.floor(durationInSeconds / 3600);
	    const minutes = Math.floor((durationInSeconds % 3600) / 60);
	    const seconds = Math.floor(durationInSeconds % 60);
	    document.getElementById('modalDuration').textContent = `${hours} giờ ${minutes} phút ${seconds} giây`;

	    const durationInHours = durationInSeconds / 3600;

	    // Tính tổng tiền dịch vụ
	    const totalPriceElements = document.querySelectorAll('.total-price');
	    let serviceTotal = 0;
	    totalPriceElements.forEach(element => {
	        serviceTotal += parseFloat(element.textContent) || 0;
	    });

	    // Tính toán tổng số tiền
	    const totalAmount = (durationInHours * courtFee + serviceTotal).toFixed(3);
	    document.getElementById('modalTotalAmount').textContent = `${totalAmount} VND`;

	    // Cập nhật danh sách dịch vụ
	    const serviceList = document.querySelectorAll('.service-select');
	    const modalServiceList = document.getElementById('modalServiceList');
	    modalServiceList.innerHTML = '';

	    serviceList.forEach(function (serviceSelect, index) {
	        const serviceName = serviceSelect.options[serviceSelect.selectedIndex].textContent;
	        const quantity = document.querySelectorAll('.quantity')[index].value;
	        const unitPrice = document.querySelectorAll('.price')[index].value;
	        const totalServicePrice = (parseFloat(quantity) * parseFloat(unitPrice)).toFixed(3);
	        const listItem = document.createElement('li');
	        listItem.textContent = `${serviceName} - ${quantity} x ${unitPrice} VND = ${totalServicePrice} VND`;
	        modalServiceList.appendChild(listItem);
	    });

	    new bootstrap.Modal(document.getElementById('paymentModal')).show();
	});

	
	
	function updateCourtStatus(courtId, status) {
	    const courtElement = document.querySelector(`[data-court-id="${courtId}"]`);
	    if (courtElement) {
	        if (status === 1) {
	            courtElement.classList.remove('bg-info-light');
	            courtElement.classList.add('bg-danger', 'text-white');
	            courtElement.removeAttribute('onclick');
	            courtElement.querySelector('p.status').textContent = 'Đang sử dụng';
	        } else {
	            courtElement.classList.remove('bg-danger', 'text-white');
	            courtElement.classList.add('bg-info-light');
	            courtElement.setAttribute('onclick', 'selectCourt(this)');
	            courtElement.querySelector('p.status').textContent = '';
	        }
	    }
	}

	// Thay đổi event listener cho nút xác nhận thanh toán trong modal
	document.querySelector('#paymentModal .modal-footer #confirmPaymentBtn').addEventListener('click', function() {
	    // Tạo form mới
	    const form = document.createElement('form');
	    form.method = 'POST';
	    form.action = '/bill/addBillWithPayment';

	    // Lấy tất cả các giá trị cần thiết
	    const formData = {
	        currentDateTime: document.querySelector('input[name="currentDateTime"]').value,
	        courtName: document.getElementById('courtName').value,
	        courtFee: document.getElementById('courtFee').value,
	        employeeName: document.querySelector('input[name="employeeName"]').value,
	        code: document.querySelector('input[name="code"]').value,
	        checkin: document.getElementById('checkinInput').value,
	        checkout: document.getElementById('modalCheckout').textContent,
	        totalAmount: document.getElementById('modalTotalAmount').textContent.replace(' VND', '')
	    };

	    // Lấy thông tin dịch vụ
	    const serviceIds = [];
	    const quantities = [];
	    const prices = [];
	    document.querySelectorAll('#services-table tbody tr').forEach(row => {
	        const serviceSelect = row.querySelector('.service-select');
	        const quantity = row.querySelector('.quantity');
	        const price = row.querySelector('.price');
	        
	        if (serviceSelect.value) {
	            serviceIds.push(serviceSelect.value);
	            quantities.push(quantity.value);
	            prices.push(price.value);
	        }
	    });

	    // Thêm tất cả input vào form
	    Object.keys(formData).forEach(key => {
	        const input = document.createElement('input');
	        input.type = 'hidden';
	        input.name = key;
	        input.value = formData[key];
	        form.appendChild(input);
	    });

	    // Thêm các input cho dịch vụ
		if (serviceIds.length > 0) {
		    serviceIds.forEach((serviceId, index) => {
		        const serviceInput = document.createElement('input');
		        serviceInput.type = 'hidden';
		        serviceInput.name = 'serviceId';
		        serviceInput.value = serviceId;
		        form.appendChild(serviceInput);

		        const quantityInput = document.createElement('input');
		        quantityInput.type = 'hidden';
		        quantityInput.name = 'quantity';
		        quantityInput.value = quantities[index];
		        form.appendChild(quantityInput);

		        const priceInput = document.createElement('input');
		        priceInput.type = 'hidden';
		        priceInput.name = 'price';
		        priceInput.value = prices[index];
		        form.appendChild(priceInput);
		    });
		}


	    // Thêm form vào document và submit
	    document.body.appendChild(form);
	    form.submit();
	});

	
