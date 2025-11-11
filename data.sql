-- ========================================================
-- RESEARCH TRACKER SYSTEM - SAMPLE DATA INSERT SCRIPT
-- This script assumes the tables already exist.
-- Run this after the Spring Boot app creates schema.
-- ========================================================

-- 🔹 1. USERS
INSERT INTO user (id, created_at, full_name, password, role, username) VALUES
('2ad7d975-3ef8-46ed-9840-7439b93614cc', '2025-11-10 11:39:43.953609', 'Tony Stark', '$2a$10$59qCSuszmI.s8YFhkrS4Qup93aLVk04jGyv55aCj1iZ/UC691.1be', 'MEMBER', 'ts@example.com'),
('4ed8588a-0420-4822-853f-31d0d23b4975', '2025-11-09 23:08:25.531878', 'John Doe', '$2a$10$dLv4VO8n0H0L162BJjAP0OIMKMJ0t1V2AFQ9kNDy7UktRRyKplYvy', 'ADMIN', 'john@example.com'),
('d9e1ad0f-a333-4e0e-9546-362dc25e55a8', '2025-11-10 13:16:15.841684', 'Bruce Wayne', '$2a$10$glVmIhzxho0N6hs23948wOyn/pLL3iysb5/Tl2kpJM2E9j9fZRz6u', 'ADMIN', 'wayne@example.com'),
('u-admin', '2025-11-11 16:49:11.000000', 'Admin User', '$2a$10$Wn4o1fZ3F9Hrq8OtrnH1V.0oDg8ZJ96cB4ZPyaWJhW5c8sTrmVMCa', 'ADMIN', 'admin@example.com'),
('u-member', '2025-11-11 16:49:11.000000', 'Member Researcher', '$2a$10$dLv4VO8n0H0L162BJjAP0OIMKMJ0t1V2AFQ9kNDy7UktRRyKplYvy', 'MEMBER', 'member@example.com'),
('u-pi', '2025-11-11 16:49:11.000000', 'Principal Investigator', '$2a$10$dLv4VO8n0H0L162BJjAP0OIMKMJ0t1V2AFQ9kNDy7UktRRyKplYvy', 'PI', 'pi@example.com'),
('u-viewer', '2025-11-11 16:49:11.000000', 'Viewer User', '$2a$10$dLv4VO8n0H0L162BJjAP0OIMKMJ0t1V2AFQ9kNDy7UktRRyKplYvy', 'VIEWER', 'viewer@example.com');

-- 🔹 2. PROJECTS
INSERT INTO project (id, description, end_date, start_date, title, pi_id, created_at, status, summary, tags, updated_at) VALUES
('732d613d-6076-44be-9abf-b9c9cae2b478', 'Deep learning models for image recognition', '2026-03-01', '2025-11-10', 'AI Research Project', NULL, NULL, NULL, NULL, NULL, NULL),
('f5c19a56-951f-4f66-a89c-bd1758e750be', 'Deep learning models for image recognition', '2026-03-01', '2025-11-10', 'AI Research Project', NULL, NULL, NULL, NULL, NULL, NULL),
('p1', 'Phase 1 focuses on data collection and ML model building.', '2026-01-01', '2025-01-01', 'AI Climate Impact Analysis', 'u-pi', '2025-11-11 16:49:11.000000', 'ACTIVE', 'Using AI to model climate effects on agriculture', 'AI, Environment, Agriculture', '2025-11-11 16:49:11.000000');

-- 🔹 3. PROJECT MEMBERS
INSERT INTO project_members (project_id, user_id) VALUES
('732d613d-6076-44be-9abf-b9c9cae2b478', '2ad7d975-3ef8-46ed-9840-7439b93614cc'),
('p1', 'u-member'),
('p1', 'u-viewer');

-- 🔹 4. MILESTONES
INSERT INTO milestone (id, description, due_date, is_completed, title, created_by, project_id) VALUES
('m1', 'Gather climate and soil data from 3 regions', '2025-06-01', b'0', 'Initial Data Collection', 'u-pi', 'p1');

-- 🔹 5. DOCUMENTS
INSERT INTO document (id, description, title, uploaded_at, url_or_path, project_id, uploaded_by) VALUES
('d1', 'Initial project proposal document', 'Research Proposal', '2025-11-11 16:49:11.000000', '/uploads/proposal.pdf', 'p1', 'u-pi');

-- ========================================================
-- ✅ DONE. The database is now populated with sample data.
-- ========================================================
