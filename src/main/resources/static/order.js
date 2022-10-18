// 分頁取得會員訂單
var pageNo = 1;
var pageSize = 8;
var totalPage = 0;
var memId = 12;
//const page = {pageNo: pageNo,
//			pageSize: pageSize}

$(function() {
	skipPage(pageNo);
});

function skipPage(pageNo) {
	console.log(memId);
//	console.log(page);
	fetch(`/order/orders/${memId}`, {
		method: 'GET',
		headers: { 'Content-Type': 'application/json',
					'pageNo': pageNo,
					'pageSize': pageSize
					},
	})
		.then(resp => resp.json())
		.then(order => {

			$(".orderList").html("");
			$("#page-navigation").html("");
			
			// orders
			for (i = 0; i < order["length"]; i++) {
				if (order[i].status == 0) {
					order[i].status = "已取消"
				} else if (order[i].status == 1) {
					order[i].status = "已付款"
				} else if (order[i].status == 2) {
					order[i].status = "未付款"
				}

				let html = `
                    <tr class="order" data-toggle="modal" data-target="#exampleModalCenter" id="${order[i].orderId}">
		                <td>${order[i].orderId}</td>
		                <td>${order[i].gymName}</td>
		                <td>$${order[i].amount}</td>
		                <td>${order[i].orderDate.replace("T", " ")}</td>
		                <td>${order[i].status}</td>
		                <td><button class="cancel">取消訂單</button></td>
		            </tr>
                    `;

				$(".orderList").append(html);
			}

			// page-navigation
			pageNo = order[0].pageNo;
			totalPage = order[0].totalPage;

			var pageMsg = `<ul class="pagination">`
                
            if(pageNo == 1){
				pageMsg += `<li class="page-item disabled">
				<a class="page-link" href="#" aria-label="Previous">&laquo;</a></li>`	
			}else{
				pageMsg += `<li class="page-item">
                  <a class="page-link" href="javascript:void(0)" onclick="skipPage(${pageNo - 1})" aria-label="Previous">&laquo;</a></li>`	
			}
                
			for (i = 0; i < totalPage; i++) {

				if (i + 1 == pageNo) {
					pageMsg += `<li class="page-item active">
                  	<a class="page-link" href="javascript:void(0)" onclick="skipPage(${i+1})">${i + 1}
                  	<span class="sr-only">(current)</span></a></li>`
				} else {
					pageMsg += `<li class="page-item"><a class="page-link" href="javascript:void(0)" onclick="skipPage(${i+1})">${i + 1}</a></li>`
				}
			}
			
			if(pageNo == totalPage){
				pageMsg += `<li class="page-item disabled">
                  <a class="page-link" href="#" aria-label="Next"> &raquo; </a></li>`
			}else{
				pageMsg += `<li class="page-item">
                  <a class="page-link" href="javascript:void(0)" onclick="skipPage(${pageNo + 1})" aria-label="Next"> &raquo; </a></li>`
			}
			
			pageMsg += `</ul>`;
			
			$("#page-navigation").append(pageMsg);

			// 取消訂單
			let cancels = document.querySelectorAll(".cancel");
			for (let i = 0; i < cancels.length; i++) {
				cancels[i].addEventListener("click", (e) => {
					e.stopPropagation();
					let status = cancels[i].closest(".order").children[4].textContent;
					if (status !== "未付款") {
						alert("訂單已完成 或 已取消")
					} else {
						let check = confirm('確定取消訂單？');
						if (check) {
							orderId = cancels[i].closest(".order").children[0].textContent;
							fetch('/order', {
								method: 'PUT',
								headers: { 'Content-Type': 'application/json' },
								body: JSON.stringify({
									orderId: orderId,
								}),
							})
								.then(resp => resp.text())
								.then(body => {
									if (body == "successfully") {
										alert("取消成功！");
										skipPage(pageNo);
									}
								});
						}
					}

				})
			}
			
			// 訂單明細
			for (let i = 0; i < order["length"]; i++) {
				let orders = document.getElementById(`${order[i].orderId}`);
				orders.addEventListener("click", () => {
					orderId = orders.children[0].textContent;
					fetch(`/orderDetail/${orderId}`, {
						method: 'GET',
						headers: { 'Content-Type': 'application/json' },
					
					})
						.then(resp => resp.json())
						.then(detail => {
							$(".detailList").html("");

							for (i = 0; i < detail["length"]; i++) {
								if (detail[i].status == 0) {
									detail[i].status = "尚未取貨"
								} else if (detail[i].status == 1) {
									detail[i].status = "已取貨"
								} else if (detail[i].status == 2) {
									detail[i].status = "已歸還"
								} else if (detail[i].status == 3) {
									detail[i].status = "逾時未取貨"
								} else if (detail[i].status == 4) {
									detail[i].status = "逾時未歸還"
								} else if (detail[i].status == 5) {
									detail[i].status = "商品損毀"
								}

								if (detail[i].pickupTime == undefined) {
									detail[i].pickupTime = "-"
								}
								if (detail[i].returnTime == undefined) {
									detail[i].returnTime = "-"
								}

								let html = `
		                    	<tr>
		                          <td>${detail[i].prodName}</td>
		                          <td>${detail[i].idvId}</td>
		                          <td>${detail[i].pickupTime}</td>
		                          <td>${detail[i].returnTime}</td>
		                          <td>${detail[i].status}</td>
		                        </tr>`;

								$(".detailList").append(html);
							}

						});
				})

			}

		});
}
