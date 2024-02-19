/**
 * フォームで送信ボタンを押すと、確認も兼ねてパスワードを求める
 */

// 入力フォームの送信時
document.getElementById('insert-form-button').addEventListener('click', function(event) {
	var password = prompt('実行するにはパスワードを入力してください:');
	if (password === null) { // キャンセルの場合
		event.preventDefault();
	} else {
		// パスワードをフォームに追加
		var input = document.createElement('input');
		input.type = 'hidden';
		input.name = 'password';
		input.value = password;
		document.getElementById('updateCreateForm-js').appendChild(input);

		// フォームを送信
		document.getElementById('updateCreateForm-js').submit();
	}
});


// 削除実行時
document.getElementById('delete-foam-button').addEventListener('click', function(event) {
	var password = prompt('削除を実行するにはパスワードを入力してください:');
	if (password === null) { // キャンセルの場合
		event.preventDefault();
	} else {
		// パスワードをフォームに追加
		var input = document.createElement('input');
		input.type = 'hidden';
		input.name = 'password';
		input.value = password;
		document.getElementById('deleteForm-js').appendChild(input);

		// フォームを送信
		document.getElementById('deleteForm-js').submit();
	}
});