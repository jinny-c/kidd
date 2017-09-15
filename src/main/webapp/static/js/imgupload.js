function change(imgId,fileId) {
    var pic = document.getElementById(imgId);
    var file = document.getElementById(fileId);
    var ext=file.value.substring(file.value.lastIndexOf(".")+1).toLowerCase();
    if(ext!='png'&&ext!='jpg'&&ext!='jpeg'){
        return;
    }
    html5Reader(file,imgId);
}

function html5Reader(file,imgId){
    var file = file.files[0];
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function(e){
        var pic = document.getElementById(imgId);
        pic.src=this.result;
    }
}