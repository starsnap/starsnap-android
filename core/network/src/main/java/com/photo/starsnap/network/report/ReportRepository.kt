package com.photo.starsnap.network.report

import com.photo.starsnap.network.auth.dto.rs.SnapReportDto
import com.photo.starsnap.network.auth.dto.rs.UserReportDto
import com.photo.starsnap.network.dto.SliceResponseDto
import com.photo.starsnap.network.report.dto.rq.SnapReportCreateDto
import com.photo.starsnap.network.report.dto.rq.UserReportCreateDto

interface ReportRepository {
    fun snapReport(snapReportCreateDto: SnapReportCreateDto)
    fun userReport(userReportCreateDto: UserReportCreateDto)

    fun getSnapReport(size: Int, page: Int): SliceResponseDto<SnapReportDto>
    fun getUserReport(size: Int, page: Int): SliceResponseDto<UserReportDto>
}