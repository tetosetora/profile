package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.AdminResult;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // ユーザーデータの取得メソッド
    public User getUsersData() {
        Optional<User> user = userRepository.findById(1L);
        // Optionalの値を返す。値がない場合はnullを返す
        return user.orElse(null);
    }

    // ユーザー情報の更新メソッド
	public AdminResult UpdateUserData(String userIntroduction, String futureChallenges) {
		try {
			User user = userRepository.findById(1L).orElseThrow();
			user.setUserIntroduction(userIntroduction);
			user.setFutureChallenges(futureChallenges);
			userRepository.save(user);
            return new AdminResult(true, "プロフィールの更新に成功しました。");
        } catch (Exception e) {
            return new AdminResult(false, "プロフィールの更新に失敗しました。");
        }

	}

}