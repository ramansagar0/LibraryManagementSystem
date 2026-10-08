
package com.library.service;

import java.util.List;

import com.library.dao.MemberDAO;
import com.library.model.Member;

public class MemberService {

    private final MemberDAO memberDAO;

    public MemberService() {
        memberDAO = new MemberDAO();
    }

    // Add member
    public boolean addMember(Member member) {

        if (member.getName() == null ||
                member.getName().trim().isEmpty()) {
            return false;
        }

        if (member.getEmail() == null ||
                member.getEmail().trim().isEmpty()) {
            return false;
        }

        if (member.getPhone() == null ||
                member.getPhone().trim().isEmpty()) {
            return false;
        }

        return memberDAO.addMember(member);
    }

    // Get all members
    public List<Member> getAllMembers() {
        return memberDAO.getAllMembers();
    }

    // Search members
    public List<Member> searchMembers(String keyword) {

        if (keyword == null) {
            return List.of();
        }

        return memberDAO.searchMembers(keyword);
    }

    // Update member
    public boolean updateMember(Member member) {

        if (member.getMemberId() <= 0) {
            return false;
        }

        if (member.getName() == null ||
                member.getName().trim().isEmpty()) {
            return false;
        }

        if (member.getEmail() == null ||
                member.getEmail().trim().isEmpty()) {
            return false;
        }

        if (member.getPhone() == null ||
                member.getPhone().trim().isEmpty()) {
            return false;
        }

        return memberDAO.updateMember(member);
    }

    // Delete member
    public boolean deleteMember(int memberId) {

        if (memberId <= 0) {
            return false;
        }

        return memberDAO.deleteMember(memberId);
    }
}