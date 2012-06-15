/*global $ */

/**
 * Perform a request for non-cached JSON data.
 * 
 * @param type
 *            the type of request e.g. "get".
 * @param url
 *            the URL to request against.
 * @param data
 *            the parameters of the request or its payload.
 * @param success
 *            the handler to call on success.
 * @param failure
 *            the handler to call on failure.
 */
function requestJSON(type, url, data, success, failure) {

	var notifiedFailure = false;

	$.ajax({
		cache : false,
		data : data,
		dataType : "json",
		url : url,
		success : function(data) {
			if (typeof data === "string") {
				data = $.parseJSON(data);
			}
			success(data);
		},
		error : function() {
			if (!notifiedFailure) {
				failure();
				notifiedFailure = true;
			}
		}
	});
}