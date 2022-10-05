متطلبات Tarama Pay SDK
لدمج Tamara Pay SDK مع تطبيقك ، تحتاج إلى AUTH_TOKEN و API_URL وخادمك NOTIFICATION_WEBHOOK_URL:
const val API_URL = "https://api-sandbox.tamara.co/"

const val AUTH_TOKEN ="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhY2NvdW50SWQiOiI0NWQwMzAzOC1kM2I2LTQ1ODctYWY2Ny1hNDNlY2FlYjFiZDMiLCJ0eXBlIjoibWVyY2hhbnQiLCJzYWx0IjoiOTIwNDFjZDVlOTJlNDQ1MDg1ZTQ2NzgyZWFhYTY3NjkiLCJpYXQiOjE1ODc3NjM2MzksImlzcyI6IlRhbWFyYSJ9.jPwKTNMwGFZ-gueED91piwXk8EA2OcqlYDlDZwg67Oo7ec7E-7Qao89BTJc-gfdyQ8542JqdAuGlg3PlC7-he5fhCUyGAbnEVy9r2pO8ROO9sINXCkdi-CotLTnO_ENWd6AMCnNlNx7hZ1wsVCVSQS6RNZjOm8iEdHxyCRU13pLWxzsSR2WGsqplVprgeMxSdMKjLatdGEk7ipp4mVUTPba4rSYvselIOfpVvX8XDN1y_aYPIIVlCtpLeJ7MRrkyH0LBZ-4b2Ac1iDAjg51K_qvYng4xNiNEyflmy9kmtkNIrPMP1IlAR0ZEx2epAlhKU7TgIiVC1bs0hjMe6M2QMQ"

const val NOTIFICATION_WEB_HOOK_URL = "https://your.site/pushnotification"
يقوم بتهيئة SDK
قم بتهيئة خدمة تمارا قبل استخدامها:
TamaraPayment.initialize(AUTH_TOKEN, API_URL, NOTIFICATION_WEB_HOOK_URL)
إنشاء ودفع النظام
قبل إضافة معلومات الطلب ، قم بإنشاء Order by call this method مع المرجع والوصف. RefId هو المعرف الفريد الخاص بك لطلبك.
TamaraPayment.createOrder(refId, description)
هذه المعلومات إلزامية:
اضبط معلومات العميل:
TamaraPayment.setCustomerInfo(firstName, lastName,
            phoneNumber, email, isFirstOrder)
إضافة السلعة مع سعرها والضرائب والخصم:
TamaraPayment.addItem(name, referenceId ,sku, unitPrice,
                    taxAmount ,discountAmount, quantity)
حدد عنوان الشحن وعنوان الفواتير:
TamaraPayment.setShippingAddress(firstName,lastName, phone,
                    addressLine1, addressLine2, country, region, city)
TamaraPayment.setBillingAddress(firstName,lastName, phone,
                    addressLine1, addressLine2, country, region, city)
تعيين رسوم الشحن:
TamaraPayment.setShippingAmount(shippingFee)
تعيين الخصم (اختياري):
TamaraPayment.setDiscount(discount, name)
العمليات على صفحة الدفع تمارا باستخدام:
TamaraPayment.startPayment(activity)
تذييل
حقوق النشر © لعام 2022 لشركة GitHub، Inc.
التنقل في التذييل
•	شروط
•	خصوصية
•	حماية
•	حالة
•	المستندات
•	اتصل بـ GitHub
•	التسعير
•	API
•	تمرين
•	مقالات
•	حول

